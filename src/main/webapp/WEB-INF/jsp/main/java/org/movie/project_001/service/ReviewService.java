package org.movie.project_001.service;

import org.movie.project_001.models.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.UUID;

@Service
public class ReviewService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String review_path = "src/main/resources/data/reviews.json";

    public List<Review> getAllReviews()throws IOException {
        File file = new File(review_path);
        return Arrays.asList(mapper.readValue(file, Review[].class));
    }

    public Review getReviewById(UUID id)throws IOException {//
        return getAllReviews().stream()
                .filter(review -> review.getReviewId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void saveReview(Review review) throws IOException {
        List<Review> reviews = new ArrayList<>(getAllReviews());
        reviews.add(review);
        mapper.writeValue(new File(review_path), reviews);

    }
    public void updateReview(UUID id,Review updatedReview) throws IOException {
        List<Review> reviews = new ArrayList<>(getAllReviews());
        for(int i =0; i<reviews.size(); i++){
            if(reviews.get(i).getReviewId().equals(id)){
                reviews.set(i, updatedReview);
                break;
            }
        }
        mapper.writeValue(new File(review_path), reviews);
    }
    public void deleteReview(UUID id)throws IOException {
        List<Review> reviews = new ArrayList<>(getAllReviews());
        reviews.removeIf(review -> review.getReviewId().equals(id));
        mapper.writeValue(new File(review_path), reviews);
    }
}
