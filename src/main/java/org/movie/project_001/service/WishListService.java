package org.movie.project_001.service;

import org.movie.project_001.models.WishList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service

public class WishListService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String WISHLISTS_FILE = "src/main/resources/data/wishlists.json";

    public List<WishList> getAllWishlists() throws IOException {
        File file = new File(WISHLISTS_FILE);
        return Arrays.asList(mapper.readValue(file, WishList[].class));
    }

    public WishList getWishlistByUserId(UUID userId) throws IOException {
        if (userId == null) {
            return null;
        }
        return getAllWishlists().stream()
                .filter(wishlist -> wishlist.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public void addMovieToWishlist(UUID userId, UUID movieId) throws IOException {
        WishList wishlist = getWishlistByUserId(userId);
        if (wishlist == null) {
            wishlist = new WishList(userId);
            wishlist.setId(UUID.randomUUID());
        }
        wishlist.addMovieId(movieId);
        saveWishlist(wishlist);
    }

    public void removeMovieFromWishlist(UUID userId, UUID movieId) throws IOException {
        WishList wishlist = getWishlistByUserId(userId);
        if (wishlist != null) {
            wishlist.removeMovieId(movieId);
            if (wishlist.getMovieIds().isEmpty()) {
                deleteWishlist(userId);
            } else {
                saveWishlist(wishlist);
            }
        }
    }

    public void saveWishlist(WishList wishlist) throws IOException {
        List<WishList> wishlists = new ArrayList<>(getAllWishlists());
        boolean found = false;
        for (int i = 0; i < wishlists.size(); i++) {
            if (wishlists.get(i).getUserId().equals(wishlist.getUserId())) {
                wishlists.set(i, wishlist);
                found = true;
                break;
            }
        }
        if (!found) {
            wishlists.add(wishlist);
        }
        mapper.writeValue(new File(WISHLISTS_FILE), wishlists);
    }

    public void deleteWishlist(UUID userId) throws IOException {
        List<WishList> wishlists = new ArrayList<>(getAllWishlists());
        wishlists.removeIf(wishlist -> wishlist.getUserId().equals(userId));
        mapper.writeValue(new File(WISHLISTS_FILE), wishlists);
    }
}
