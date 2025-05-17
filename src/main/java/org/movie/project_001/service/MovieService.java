package org.movie.project_001.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.movie.project_001.models.Movie;
import org.movie.project_001.models.Review;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class MovieService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String movie_path = "src/main/resources/data/movies.json";

    public List<Movie> getAllMovies() throws IOException {
        File movie_file = new File(movie_path);
        List<Movie> movies = Arrays.asList(mapper.readValue(movie_file, Movie[].class));

        for (Movie movie : movies) {
            movie.setRating(calculateAverageRating(movie.getReviews()));
        }

        return movies;
    }

    public Movie getMovieById(UUID movie_id) throws IOException {
        return getAllMovies().stream()
                .filter(movie -> movie.getId().equals(movie_id))
                .findFirst()
                .orElse(null);
    }

    public void saveMovie(Movie movie) throws IOException {
        List<Movie> movies = new ArrayList<>(getAllMovies());
        movies.add(movie);
        mapper.writeValue(new File(movie_path), movies);
    }

    public void updateMovie(UUID id, Movie updatedMovie) throws IOException {
        List<Movie> movies = new ArrayList<>(getAllMovies());
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId().equals(id)) {
                updatedMovie.setRating(calculateAverageRating(updatedMovie.getReviews()));
                movies.set(i, updatedMovie);
                break;
            }
        }
        mapper.writeValue(new File(movie_path), movies);
    }

    public void deleteMovie(UUID id) throws IOException {
        List<Movie> movies = new ArrayList<>(getAllMovies());
        movies.removeIf(movie -> movie.getId().equals(id));
        mapper.writeValue(new File(movie_path), movies);
    }

    public List<Movie> searchMoviesByTitle(String title) throws IOException {
        List<Movie> allMovies = getAllMovies();
        List<Movie> matchingMovies = new ArrayList<>();
        for (Movie movie : allMovies) {
            if (movie.getTitle() != null && movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                matchingMovies.add(movie);
            }
        }
        return matchingMovies;
    }

    public void addReview(UUID movieId, Review review) throws IOException {
        List<Movie> movies = new ArrayList<>(getAllMovies());
        for (Movie movie : movies) {
            if (movie.getId().equals(movieId)) {
                if (movie.getReviews() == null) {
                    movie.setReviews(new ArrayList<>());
                }
                movie.getReviews().add(review);
                movie.setRating(calculateAverageRating(movie.getReviews()));
                break;
            }
        }
        mapper.writeValue(new File(movie_path), movies);
    }

    public void deleteReview(UUID movieId, UUID reviewId, UUID userId) throws IOException {
        List<Movie> movies = new ArrayList<>(getAllMovies());
        for (Movie movie : movies) {
            if (movie.getId().equals(movieId) && movie.getReviews() != null) {
                movie.getReviews().removeIf(
                        r -> r.getReviewId().equals(reviewId) && r.getUserId().equals(userId)
                );
                movie.setRating(calculateAverageRating(movie.getReviews()));
                break;
            }
        }
        mapper.writeValue(new File(movie_path), movies);
    }

    public void updateReview(UUID movieId, UUID reviewId, UUID userId, int rating, String content) throws IOException {
        List<Movie> movies = new ArrayList<>(getAllMovies());
        for (Movie movie : movies) {
            if (movie.getId().equals(movieId)) {
                if (movie.getReviews() != null) {
                    for (Review review : movie.getReviews()) {
                        if (review.getReviewId().equals(reviewId) && review.getUserId().equals(userId)) {
                            review.setRating(rating);
                            review.setContent(content);
                            break;
                        }
                    }
                }
                movie.setRating(calculateAverageRating(movie.getReviews()));
                break;
            }
        }
        mapper.writeValue(new File(movie_path), movies);
    }

    public Review getReviewById(UUID movieId, UUID reviewId) throws IOException {
        Movie movie = getMovieById(movieId);
        if (movie != null && movie.getReviews() != null) {
            for (Review review : movie.getReviews()) {
                if (review.getReviewId().equals(reviewId)) {
                    return review;
                }
            }
        }
        return null;
    }

    private double calculateAverageRating(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) return 0.0;
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        return Math.round((sum / reviews.size()) * 10.0) / 10.0;
    }
}
