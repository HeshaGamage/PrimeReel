package org.movie.project_001.models;

import java.util.List;
import java.util.UUID;

public class Movie {
    private final UUID id = UUID.randomUUID();
    private String title;
    private String description;
    private Double rating;
    private boolean available;
    private List<Review> reviews; 
    private String posterUrl;

    // Constructors
    public Movie() {
    }

    public Movie(String description, Double rating, boolean available, String title) {
        this.description = description;
        this.rating = rating;
        this.available = available;
        this.title = title;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
