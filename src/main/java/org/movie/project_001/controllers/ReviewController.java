package org.movie.project_001.controllers;

import org.movie.project_001.models.Review;
import org.movie.project_001.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.io.IOException;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() throws IOException {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable UUID id) throws IOException {//todo change this to get review by movie
        return reviewService.getReviewById(id);
    }

    @PostMapping
    public void saveReview(@RequestBody Review review) throws IOException {
        reviewService.saveReview(review);
    }

    @PutMapping("/{id}")
    public void updateReview(@PathVariable UUID id, @RequestBody Review updatedReview) throws IOException {
        reviewService.updateReview(id, updatedReview);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable UUID id) throws IOException {
        reviewService.deleteReview(id);
    }
}