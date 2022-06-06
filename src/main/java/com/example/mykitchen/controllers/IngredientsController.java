package com.example.mykitchen.controllers;


import com.example.mykitchen.model.Ingredient;
import com.example.mykitchen.services.IngredientsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class IngredientsController {
    private final IngredientsService is;

    public IngredientsController(IngredientsService is){
        this.is =is;
    }

    @GetMapping
    public List<Ingredient> getAll(){
        return is.getAllIngredients();
    }

    @GetMapping("/{name}")
    public Ingredient getIngredientByName(@PathVariable String name){
        Ingredient i = is.getIngredientByName(name);
        if(i!=null){
            return i;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"can't find this ingredient!");
        }
    }

    @GetMapping("/search")
    public List<Ingredient> find(@RequestParam String search, @RequestParam(required = false,defaultValue = "0") int weight){
        return is.SearchIngredientByNameWeight(search,weight);
    }

    @PostMapping(consumes = {"application/json"})
    public Ingredient addIngredientJson(@RequestBody Ingredient i){
        return is.saveIngredient(i);
    }


    @DeleteMapping
    public void deleteAll(){
        is.deleteAll();
    }

//    @DeleteMapping("/{name}")
//    public void deleteIngredient(@PathVariable String name){
//        if(!this.is.ingredientExists(name)){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with name " + name + " not found!");
//        }
//        is.deleteIngredientByName(name);
//    }

    @DeleteMapping("/{id}")
    public Object deleteIngredient(@PathVariable Long id){
        if(!this.is.ingredientExists(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id " + id + " not found!");
        }
        return is.deleteIngredientById(id);
    }

    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable Long id,@RequestBody Ingredient i){
        return this.is.updateIngredient(id,i);
    }






}
