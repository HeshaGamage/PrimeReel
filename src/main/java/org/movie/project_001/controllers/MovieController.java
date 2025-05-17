package org.movie.project_001.controllers;

import org.movie.project_001.models.Movie;
import org.movie.project_001.models.Review;
import org.movie.project_001.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Get all movies
    @GetMapping
    public List<Movie> getAllMovies() throws IOException {
        return movieService.getAllMovies();
    }

    // Get movie by ID
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable UUID id) throws IOException {
        return movieService.getMovieById(id);
    }

    // Search movies by title
    @GetMapping("/search")
    public List<Movie> searchMoviesByTitle(@RequestParam String title) throws IOException {
        return movieService.searchMoviesByTitle(title);
    }

    // Create new movie
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMovie(@RequestBody Movie movie) throws IOException {
        movieService.saveMovie(movie);
    }

    // Update movie
    @PutMapping("/{id}")
    public void updateMovie(@PathVariable UUID id, @RequestBody Movie updatedMovie) throws IOException {
        movieService.updateMovie(id, updatedMovie);
    }

    // Delete movie
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable UUID id) throws IOException {
        movieService.deleteMovie(id);
    }


}
