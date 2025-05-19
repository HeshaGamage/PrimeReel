package org.movie.project_001.service;

import org.movie.project_001.models.Rental;
import org.movie.project_001.models.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.util.stream.Collectors;

@Service
public class RentalService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String rent_path = "src/main/resources/data/rental.json";

    private final Stack<Rental> rentalStack = new Stack<>();

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    public List<Rental> getAllRental() throws IOException {
        File file = new File(rent_path);
        return Arrays.asList(mapper.readValue(file, Rental[].class));
    }

    public List<Rental> getRentalByUserId(UUID userId) throws IOException {
        return getAllRental().stream()
                .filter(rental -> rental.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Stack<Rental> getRentalStack() {
        return rentalStack;
    }

    public void saveRental(Rental rental) throws IOException {
        List<Rental> rentals = new ArrayList<>(getAllRental());
        rentals.add(rental);
        mapper.writeValue(new File(rent_path), rentals);
    }

    public void updateRental(UUID id, Rental updatedRental) throws IOException {
        List<Rental> rentals = new ArrayList<>(getAllRental());
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getRentalId().equals(id)) {
                rentals.set(i, updatedRental);
                break;
            }
        }
        mapper.writeValue(new File(rent_path), rentals);
    }

    public void deleteRental(UUID id) throws IOException {
        List<Rental> rentals = new ArrayList<>(getAllRental());
        rentals.removeIf(rental -> rental.getRentalId().equals(id));
        mapper.writeValue(new File(rent_path), rentals);
    }

    public void returnMovie(UUID rentalId) throws IOException {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        List<Rental> rentals = new ArrayList<>(getAllRental());
        for (Rental rental : rentals) {
            if (rental.getRentalId().equals(rentalId) && rental.getReturnDate() == null) {
                rental.setReturnDate(date);
                break;
            }
        }
        mapper.writeValue(new File(rent_path), rentals);
    }

    public Optional<Rental> getRentalByUserAndMovieId(UUID userId, UUID movieId) throws IOException {
        return getAllRental().stream()
                .filter(rental -> rental.getUserId().equals(userId) && rental.getMovieId().equals(movieId))
                .findFirst();
    }

    public void rentMovie(UUID movieId, UUID userId) throws IOException {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Rental rental = new Rental(movieId, userId, date, null);

        List<Rental> rentals = new ArrayList<>(getAllRental());

        Optional<Rental> alreadyRentedMovie = getRentalByUserAndMovieId(userId, movieId);
        if (alreadyRentedMovie.isEmpty()) {
            rentals.add(rental);
            mapper.writeValue(new File(rent_path), rentals);
            rentalStack.push(rental);

            Movie movie = movieService.getMovieById(movieId);
            if (movie != null && movie.isAvailable()) {
                movie.setAvailable(false);
                movieService.updateMovie(movieId, movie);
            }
        }
    }

    public void removeRental(UUID userId, UUID movieId) throws IOException {
        List<Rental> rentals = getRentalByUserId(userId);
        for (Rental rental : rentals) {
            if (rental.getMovieId().equals(movieId)) {
                deleteRental(rental.getRentalId());

                Movie movie = movieService.getMovieById(movieId);
                if (movie != null) {
                    movie.setAvailable(true);
                    movieService.updateMovie(movieId, movie);
                }
                break;
            }
        }
    }
}
