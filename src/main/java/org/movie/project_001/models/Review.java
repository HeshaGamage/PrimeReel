package org.movie.project_001.models;

import java.util.UUID;

public class Review {
    private UUID reviewId = UUID.randomUUID();
    private UUID userId;
    private String username;
    private String content;
    private int rating;

    // Constructors
    public Review() {
    }

    public Review(UUID userId, String username, String content, int rating) {
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.rating = rating;
    }

    // Getters and Setters
    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
