package org.movie.project_001.models;

import java.util.UUID;

public class Rental {
    private final UUID rentalId = UUID.randomUUID();
    private UUID movieId;
    private UUID userId;
    private String rentalDate;
    private String returnDate;

    public Rental() {
    }

    public Rental(UUID movieId, UUID userId, String rentalDate, String returnDate) {
        this.movieId = movieId;
        this.userId = userId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public UUID getRentalId() {
        return rentalId;
    }

    public UUID getMovieId() {
        return movieId;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
