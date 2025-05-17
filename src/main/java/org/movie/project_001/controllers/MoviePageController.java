package org.movie.project_001.controllers;

import jakarta.servlet.http.HttpSession;
import org.movie.project_001.models.Movie;
import org.movie.project_001.models.Review;
import org.movie.project_001.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class MoviePageController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movie/view/{id}")
public String viewMoviePage(@PathVariable UUID id,
                            @RequestParam(value = "editingReviewId", required = false) UUID editingReviewId,
                            Model model,
                            HttpSession session) throws IOException {
    Movie movie = movieService.getMovieById(id);
    if (movie == null) return "redirect:/home";

    List<Review> reviews = movie.getReviews();
    if (reviews != null) {
        // Bubble Sort from low to high rating
        for (int i = 0; i < reviews.size() - 1; i++) {
            for (int j = 0; j < reviews.size() - i - 1; j++) {
                if (reviews.get(j).getRating() > reviews.get(j + 1).getRating()) {
                    Review temp = reviews.get(j);
                    reviews.set(j, reviews.get(j + 1));
                    reviews.set(j + 1, temp);
                }
            }
        }
    }

    model.addAttribute("movie", movie);
    model.addAttribute("editingReviewId", editingReviewId);
    model.addAttribute("currentUserId", session.getAttribute("userId"));

    return "movie-details";
}


    

    // Load edit review form
    @GetMapping("/movie/{movieId}/reviews/{reviewId}/edit")
    public String showEditForm(@PathVariable UUID movieId,
                               @PathVariable UUID reviewId,
                               @RequestParam UUID userId,
                               Model model) throws IOException {

        Movie movie = movieService.getMovieById(movieId);
        Review review = movieService.getReviewById(movieId, reviewId);

        if (movie == null || review == null || !review.getUserId().equals(userId)) {
            return "redirect:/movie/view/" + movieId;
        }

        model.addAttribute("movie", movie);
        model.addAttribute("review", review);
        model.addAttribute("userId", userId);
        return "edit-review"; // Ensure you have edit-review.jsp
    }

    // Submit edited review
    @PostMapping("/movie/{movieId}/reviews/{reviewId}/edit")
    public String updateReview(@PathVariable UUID movieId,
                               @PathVariable UUID reviewId,
                               @RequestParam UUID userId,
                               @RequestParam int rating,
                               @RequestParam String content) throws IOException {

        movieService.updateReview(movieId, reviewId, userId, rating, content);
        return "redirect:/movie/view/" + movieId;
    }

    // Delete review
    @PostMapping("/movie/{movieId}/reviews/{reviewId}")
    public String deleteReview(@PathVariable UUID movieId,
                               @PathVariable UUID reviewId,
                               @RequestParam UUID userId) throws IOException {

        movieService.deleteReview(movieId, reviewId, userId);
        return "redirect:/movie/view/" + movieId;
    }

    @PostMapping("/movie/{movieId}/reviews")
public String addReviewFromForm(@PathVariable UUID movieId,
                                @RequestParam int rating,
                                @RequestParam String content,
                                @RequestParam UUID userId,
                                @RequestParam String username) throws IOException {
    Review review = new Review(userId, username, content, rating);
    movieService.addReview(movieId, review);
    return "redirect:/movie/view/" + movieId;
}

}
