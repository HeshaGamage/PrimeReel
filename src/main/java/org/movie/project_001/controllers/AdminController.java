package org.movie.project_001.controllers;

import jakarta.servlet.http.HttpSession;
import org.movie.project_001.models.Movie;
import org.movie.project_001.models.User;
import org.movie.project_001.service.MovieService;
import org.movie.project_001.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @GetMapping("")
    public String adminDashboard(Model model) throws IOException {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin"; // admin.jsp
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam UUID userId, HttpSession session) throws IOException {
        UUID currentAdminId = (UUID) session.getAttribute("userId");

        if (!userId.equals(currentAdminId)) {
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @PostMapping("/delete-movie")
    public String deleteMovie(@RequestParam UUID movieId) throws IOException {
        movieService.deleteMovie(movieId);
        return "redirect:/admin";
    }

    @PostMapping("/add-movie")
public String addMovie(@RequestParam String title,
                       @RequestParam String description,
                       @RequestParam Double rating,
                       @RequestParam boolean available,
                       @RequestParam(required = false) String posterUrl) throws IOException {

    Movie movie = new Movie();
    movie.setTitle(title);
    movie.setDescription(description);
    movie.setRating(rating);
    movie.setAvailable(available);
    movie.setPosterUrl(posterUrl);

    movieService.saveMovie(movie);
    return "redirect:/admin";
}
@PostMapping("/update-movie")
public String updateMovie(@RequestParam UUID movieId,
                          @RequestParam String title,
                          @RequestParam String description,
                          @RequestParam boolean available,
                          @RequestParam(required = false) String posterUrl) throws IOException {
    Movie movie = movieService.getMovieById(movieId);
    if (movie != null) {
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setAvailable(available);
        movie.setPosterUrl(posterUrl);
        movieService.updateMovie(movieId, movie);
    }
    return "redirect:/admin";
}



}
