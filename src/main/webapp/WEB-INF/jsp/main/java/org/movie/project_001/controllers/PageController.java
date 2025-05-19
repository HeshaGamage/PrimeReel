package org.movie.project_001.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.movie.project_001.datastructures.MovieArray;
import org.movie.project_001.datastructures.MovieStack;
import org.movie.project_001.models.Movie;
import org.movie.project_001.models.Rental;
import org.movie.project_001.models.User;
import org.movie.project_001.models.WishList;
import org.movie.project_001.service.MovieService;
import org.movie.project_001.service.RentalService;
import org.movie.project_001.service.UserService;
import org.movie.project_001.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class PageController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private WishListService wishlistService;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String homePage(HttpServletRequest request,
                           Model model,
                           @SessionAttribute("userId") UUID userId,
                           @RequestParam(value = "query", required = false) String query) throws IOException {

        List<Movie> movies;

        if (query != null && !query.trim().isEmpty()) {
            movies = movieService.searchMoviesByTitle(query.trim());
            model.addAttribute("searchQuery", query.trim());
        } else {
            List<Movie> allMovies = movieService.getAllMovies();
            MovieArray movieArray = new MovieArray(allMovies.size());
            for (Movie movie : allMovies) {
                movieArray.add(movie);
            }
            movieArray.bubbleSortByRating(); // sort descending by rating
            movies = movieArray.toList();
        }

        model.addAttribute("movies", movies);

        List<Rental> rentals = rentalService.getRentalByUserId(userId);
        UUID[] rentedIds = new UUID[rentals.size()];
        for (int i = 0; i < rentals.size(); i++) {
            rentedIds[i] = rentals.get(i).getMovieId();
        }

        model.addAttribute("rentedMovieIds", rentedIds);

        request.setAttribute("originalPath", "/home");
        request.setAttribute("contentPage", "/WEB-INF/jsp/pages/home.jsp");
        request.setAttribute("pageCss", "/css/home.css");
        return "main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String redirectSignup() {
        return "redirect:/signup";
    }

    @GetMapping("/wishlist")
    public String wishlistPage(HttpServletRequest request,
                               Model model,
                               HttpSession session,
                               @RequestParam(value = "wishlistMessage", required = false) String wishlistMessage) throws IOException {

        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        WishList wishlist = wishlistService.getWishlistByUserId(userId);
        MovieArray movieArray = new MovieArray(wishlist != null ? wishlist.getMovieIds().size() : 0);

        if (wishlist != null) {
            for (UUID movieId : wishlist.getMovieIds()) {
                Movie movie = movieService.getMovieById(movieId);
                if (movie != null) movieArray.add(movie);
            }
        }

        model.addAttribute("movies", movieArray.toList());
        if (wishlistMessage != null) model.addAttribute("wishlistMessage", wishlistMessage);

        request.setAttribute("originalPath", "/wishlist");
        request.setAttribute("contentPage", "/WEB-INF/jsp/pages/wishlist.jsp");
        request.setAttribute("pageCss", "/css/wishlist.css");
        return "main";
    }

    @GetMapping("/rented")
    public String rentedPage(HttpServletRequest request,
                             Model model,
                             HttpSession session) throws IOException {

        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        List<Rental> rentals = rentalService.getRentalByUserId(userId);
        MovieStack stack = new MovieStack(rentals.size());

        for (Rental rental : rentals) {
            Movie movie = movieService.getMovieById(rental.getMovieId());
            if (movie != null) {
                stack.push(movie);
            }
        }

        model.addAttribute("movies", stack.getAll());
        request.setAttribute("originalPath", "/rented");
        request.setAttribute("contentPage", "/WEB-INF/jsp/pages/rented.jsp");
        request.setAttribute("pageCss", "/css/rented.css");
        return "main";
    }
}
