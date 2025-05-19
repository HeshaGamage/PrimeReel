package org.movie.project_001.controllers;

import org.movie.project_001.models.Rental;
import org.movie.project_001.service.MovieService;
import org.movie.project_001.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private MovieService movieService;

    @GetMapping
    @ResponseBody
    public List<Rental> getAllRentals() throws IOException {
        return rentalService.getAllRental();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public List<Rental> getRentalByUserId(@PathVariable UUID id) throws IOException {
        return rentalService.getRentalByUserId(id);
    }

    @PostMapping
    public String rentMovie(@RequestParam UUID movieId,
                            @RequestParam UUID userId) throws IOException {
        rentalService.rentMovie(movieId, userId);
        return "redirect:/home";
    }

    @PostMapping("/unrent")
    public String unrentMovie(@RequestParam UUID movieId,
                              @RequestParam UUID userId) throws IOException {
        rentalService.removeRental(userId, movieId);
        return "redirect:/home";
    }

    @PutMapping("/return/{id}")
    @ResponseBody
    public void returnMovie(@PathVariable UUID id) throws IOException {
        rentalService.returnMovie(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public void updateRental(@PathVariable UUID id,
                             @RequestBody Rental updatedRental) throws IOException {
        rentalService.updateRental(id, updatedRental);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteRental(@PathVariable UUID id) throws IOException {
        rentalService.deleteRental(id);
    }

    @GetMapping("/last-rented")
    @ResponseBody
    public Rental getLastRented() {
        Stack<Rental> stack = rentalService.getRentalStack();
        return stack.isEmpty() ? null : stack.peek();
    }

    @PostMapping("/undo")
    @ResponseBody
    public String undoLastRental() throws IOException {
        Stack<Rental> stack = rentalService.getRentalStack();
        if (!stack.isEmpty()) {
            Rental lastRental = stack.pop();
            rentalService.removeRental(lastRental.getUserId(), lastRental.getMovieId());
            return "Undone rental for movie ID: " + lastRental.getMovieId();
        }
        return "Nothing to undo.";
    }
}
