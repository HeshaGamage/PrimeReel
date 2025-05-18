package org.movie.project_001.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WishList {
    private UUID id;
    private UUID userId;
    private List<UUID> movieIds;

    public WishList() {
        this.movieIds = new ArrayList<>();
    }

    public WishList(UUID userId) {
        this.userId = userId;
        this.movieIds = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<UUID> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(List<UUID> movieIds) {
        this.movieIds = movieIds;
    }

    public void addMovieId(UUID movieId) {
        if (!movieIds.contains(movieId)) {
            movieIds.add(movieId);
        }
    }

    public void removeMovieId(UUID movieId) {
        movieIds.remove(movieId);
    }
}
