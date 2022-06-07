package com.example.mykitchen.services;

import com.example.mykitchen.model.Dish;
import com.example.mykitchen.model.Review;
import com.example.mykitchen.repositories.DishRepository;
import com.example.mykitchen.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository=reviewRepository;
    }

    public boolean reviewExists(Long id) {
        List<Review> reviews = reviewRepository.findAll();
        if (id != null) {
            for (Review r: reviews) {
                if(Objects.equals(r.getId(), id)){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * get
     */
    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public Optional<Review> getOneReviewById(Long id){
        return reviewRepository.findById(id);
    }

    public List<Review> SearchReviewByScore(int score){
        List<Review> reviews = reviewRepository.findAll();
            List<Review> searchReviewScore = new ArrayList<>();
            for (Review r: reviews) {
                if(r.score==score){
                    searchReviewScore.add(r);
                }
            }
            return searchReviewScore;
    }

    /**
     * add
     */

    public Review saveReview(Review r){
        return reviewRepository.save(r);
    }

    /**
     * modify
     */
    public Review updateReview(Long id, Review i){
        List<Review> reviews = reviewRepository.findAll();
        if (id != null && i !=null) {
            for (Review review:reviews) {
                if(Objects.equals(review.getId(), id)){
                    review.setScore(i.getScore());
                    review.setDescription(i.getDescription());
                    reviewRepository.save(review);
                    return review;
                }
            }
        }
        return null;
    }

    /**
     *Delete
     */
    public Object deleteReviewById(Long id){
        try {
            reviewRepository.deleteById(id);
        }catch (Exception e){
            return  HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        return  HttpServletResponse.SC_OK;
    }

    public void deleteAll(){
        reviewRepository.deleteAll();
    }

}
