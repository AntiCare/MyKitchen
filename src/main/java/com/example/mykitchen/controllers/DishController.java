package com.example.mykitchen.controllers;


import com.example.mykitchen.model.Dish;
import com.example.mykitchen.services.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dishes")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class DishController {
    private final DishService fs;

    public DishController(DishService fs){
        this.fs =fs;
    }

    /**
     * GET -get all dishes.
     * @return List<Dish>
     */
    @GetMapping
    public List<Dish> getAll(){
        return fs.getAllDishes();
    }

    /**
     * GET -get one dish by id.
     * @param id the id of dish.
     * @return dish or HTTP status 404.
     */
    @GetMapping("/{id}")
    public Optional<Dish> getDishByID(@PathVariable Long id){
        Optional<Dish> f = fs.getOneFoodById(id);
        if(f.isPresent()){
            return f;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"can't find this dish!");
        }
    }

    /**
     * GET -Search for dishes with matching name .
     * @param search the name of dish
     * @return List<Dish>
     */
    @GetMapping("/search")
    public List<Dish> find(@RequestParam String search){
        return fs.SearchDishByNameTime(search);
    }

    /**
     * POST -add new dish
     * @param f -new dish
     * @return HTTP status 201 or 500.
     */
    @PostMapping(consumes = {"application/json"})
    public Object addFoodJson(@RequestBody Dish f){
        return fs.saveDish(f);
    }

    /**
     * PUT -update exist dish
     * @param id  find dish by id
     * @param fo new dish
     * @return HTTP status 201 or 500.
     */
    @PutMapping("/{id}")
    public Object updateFood(@PathVariable Long id, @RequestBody Dish fo){
        return this.fs.updateDish(id,fo);
    }

    /**
     * DELETE -delete all dishes.
     */
    @DeleteMapping
    public void deleteAll(){
        fs.deleteAll();
    }

    /**
     * DELETE -delete dish by id.
     * @param id id
     * @return 1. 404 2. HTTP status 200 or 500.
     */
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Long id){
        if(!this.fs.dishExists(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with id " + id + " not found!");
        }
        return fs.deleteDishById(id);
    }








}
