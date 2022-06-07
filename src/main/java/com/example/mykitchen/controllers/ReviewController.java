package com.example.mykitchen.controllers;

import com.example.mykitchen.model.Dish;
import com.example.mykitchen.model.Review;
import com.example.mykitchen.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ReviewController {

    private final ReviewService rs;

    public ReviewController(ReviewService rs){
        this.rs=rs;
    }

    @GetMapping
    public List<Review> getAll(){
        return rs.getAllReviews();
    }

    @GetMapping("/search")
    public List<Review> find(@RequestParam int search){
        return rs.SearchReviewByScore(search);
    }

    @PostMapping(consumes = {"application/json"})
    public Review addReviewJson(@RequestBody Review r){
        return rs.saveReview(r);
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review r){
        return this.rs.updateReview(id,r);
    }

    @DeleteMapping
    public void deleteAll(){
        rs.deleteAll();
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Long id){
        if(!this.rs.reviewExists(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with id " + id + " not found!");
        }
        return rs.deleteReviewById(id);
    }

}
