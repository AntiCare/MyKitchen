package com.example.mykitchen.services;

import com.example.mykitchen.model.Ingredient;
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

    /**
     * Check if review exist by id
     * @param id review id
     * @return bool
     */
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
     * GET - get all reviews
     * @return List<Review>
     */
    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    /**
     * GET - find review by id
     * @param id review id
     * @return Optional<Review>
     */
    public Optional<Review> getOneReviewById(Long id){
        return reviewRepository.findById(id);
    }

    /**
     * GET - search review by score
     * @param score review score
     * @return List<Review>
     */
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
     * POST - add new review.
     * @param r new review
     * @return HTTP status 201 or 500
     */
    public Object saveReview(Review r){
        try{
            reviewRepository.save(r);
            return HttpServletResponse.SC_CREATED;
        }catch (Exception e){
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }

    }

    /**
     * PUT - modify review by id
     * @param id ingredient id
     * @param i new review
     * @return HTTP status 201 or 500
     */
    public Object updateReview(Long id, Review i){
        List<Review> reviews = reviewRepository.findAll();
        if (id != null && i !=null) {
            for (Review review:reviews) {
                if(Objects.equals(review.getId(), id)){
                    review.setScore(i.getScore());
                    review.setDescription(i.getDescription());
                    reviewRepository.save(review);
                    return HttpServletResponse.SC_CREATED;
                }
            }
        }
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    /**
     * DELETE - delete review by id.
     * @param id review id
     * @return HTTP status 200 or 500
     */
    public Object deleteReviewById(Long id){
        try {
            reviewRepository.deleteById(id);
        }catch (Exception e){
            return  HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        return  HttpServletResponse.SC_OK;
    }
    /**
     * DELETE - delete all reviews.
     */
    public void deleteAll(){
        reviewRepository.deleteAll();
    }

}
