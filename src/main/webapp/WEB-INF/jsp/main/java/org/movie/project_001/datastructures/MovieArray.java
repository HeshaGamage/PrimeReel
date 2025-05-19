package org.movie.project_001.datastructures;

import org.movie.project_001.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieArray {
    private Movie[] movies;
    private int size;

    public MovieArray(int capacity) {
        this.movies = new Movie[capacity];
        this.size = 0;
    }

    public void add(Movie movie) {
        if (size < movies.length) {
            movies[size++] = movie;
        }
    }

    public void bubbleSortByRating() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (movies[j].getRating() < movies[j + 1].getRating()) {
                    Movie temp = movies[j];
                    movies[j] = movies[j + 1];
                    movies[j + 1] = temp;
                }
            }
        }
    }

    public List<Movie> toList() {
        List<Movie> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(movies[i]);
        }
        return list;
    }
}
