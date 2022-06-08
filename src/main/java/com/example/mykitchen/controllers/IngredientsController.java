package com.example.mykitchen.controllers;


import com.example.mykitchen.model.Ingredient;
import com.example.mykitchen.services.IngredientsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class IngredientsController {
    private final IngredientsService is;

    public IngredientsController(IngredientsService is){
        this.is =is;
    }

    /**
     * GET -get all Ingredients.
     * @return List<Ingredient>
     */
    @GetMapping
    public List<Ingredient> getAll(){
        return is.getAllIngredients();
    }

    /**
     * GET -get one ingredient by id.
     * @param id the id of ingredient.
     * @return ingredient or HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}")
    public Optional<Ingredient> getIngredientByID(@PathVariable Long id){
        Optional<Ingredient> i = is.getOneIngredientById(id);
        if(i!=null){
            return i;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"can't find this ingredient!");
        }
    }

    /**
     * GET -Search for ingredients with matching name .
     * @param search the name of ingredient
     * @return List<Ingredient>
     */
    @GetMapping("/search")
    public List<Ingredient> find(@RequestParam String search){
        return is.SearchIngredientByNameWeight(search);
    }

    /**
     * POST -add new ingredient
     * @param i -new ingredient
     * @return HTTP status 201 or 500.
     */
    @PostMapping(consumes = {"application/json"})
    public Object addIngredientJson(@RequestBody Ingredient i){
        return is.saveIngredient(i);
    }

    /**
     * PUT -update exist ingredient
     * @param id  find ingredient by id
     * @param i new ingredient
     * @return HTTP status 201 or 500.
     */
    @PutMapping("/{id}")
    public Object updateIngredient(@PathVariable Long id,@RequestBody Ingredient i){
        return this.is.updateIngredient(id,i);
    }

    /**
     * DELETE -delete all ingredients.
     */
    @DeleteMapping
    public void deleteAll(){
        is.deleteAll();
    }

    /**
     * DELETE -delete ingredient by id.
     * @param id id
     * @return 1. 404 2. HTTP status 200 or 500.
     */
    @DeleteMapping("/{id}")
    public Object deleteIngredient(@PathVariable Long id){
        if(!this.is.ingredientExists(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id " + id + " not found!");
        }
        return is.deleteIngredientById(id);
    }

}
