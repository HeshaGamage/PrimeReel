package org.movie.project_001.controllers;

import org.movie.project_001.models.WishList;
import org.movie.project_001.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController  //  Use RestController for proper JSON response
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishlistService;

    @GetMapping("/all")
    public List<WishList> getAllWishlists() throws IOException {
        return wishlistService.getAllWishlists();
    }

    @GetMapping("/{userId}")
    public WishList getWishlistByUserId(@PathVariable UUID userId) throws IOException {
        return wishlistService.getWishlistByUserId(userId);
    }


    @PostMapping("/add")
    public ResponseEntity<String> addMovieToWishlist(@RequestParam UUID userId,
                                                     @RequestParam UUID movieId) throws IOException {
        wishlistService.addMovieToWishlist(userId, movieId);
        return ResponseEntity.ok("Movie added to wishlist");
    }


    @PostMapping("/remove")
    public ResponseEntity<String> removeMovieFromWishlist(@RequestParam UUID userId,
                                                          @RequestParam UUID movieId) throws IOException {
        wishlistService.removeMovieFromWishlist(userId, movieId);
        return ResponseEntity.ok("Movie removed from wishlist");
    }
}
