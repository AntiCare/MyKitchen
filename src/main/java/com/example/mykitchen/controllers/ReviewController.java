package com.example.mykitchen.controllers;

import com.example.mykitchen.model.Review;
import com.example.mykitchen.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ReviewController {

    private final ReviewService rs;


    public ReviewController(ReviewService rs){
        this.rs=rs;
    }
    /**
     * GET -get all reviews.
     * @return List<Review>
     */
    @GetMapping
    public List<Review> getAll(){
        return rs.getAllReviews();
    }

    /**
     * GET -get one review by id.
     * @param id the id of review.
     * @return review or HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}")
    public Optional<Review> getReviewByID(@PathVariable Long id){
        Optional<Review> r = rs.getOneReviewById(id);
        if(r.isPresent()){
            return r;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"can't find this review!");
        }
    }

    /**
     * GET -Search for reviews with matching score .
     * @param search the score of review
     * @return List<Review> or
     */
    @GetMapping("/search")
    public Object find(@RequestParam(defaultValue = "0") int search){
        return rs.SearchReviewByScore(search);
    }

    /**
     * POST -add new review.
     * @param r -new review
     * @return HTTP status 201 or 500.
     */
    @PostMapping(consumes = {"application/json"})
    public Object addReviewJson(@RequestBody Review r){
        return rs.saveReview(r);
    }

    /**
     * PUT -update exist review.
     * @param id  find review by id
     * @param r new review
     * @return HTTP status 201 or 500.
     */
    @PutMapping("/{id}")
    public Object updateReview(@PathVariable Long id, @RequestBody Review r){
        return this.rs.updateReview(id,r);
    }

    /**
     * DELETE -delete all reviews.
     */
    @DeleteMapping
    public void deleteAll(){
        rs.deleteAll();
    }

    /**
     * DELETE -delete review by id.
     * @param id id
     * @return 1. 404 2. HTTP status 200 or 500.
     */
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Long id){
        if(!this.rs.reviewExists(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with id " + id + " not found!");
        }
        return rs.deleteReviewById(id);
    }

}
