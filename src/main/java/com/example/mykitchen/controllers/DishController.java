package com.example.mykitchen.controllers;


import com.example.mykitchen.model.Dish;
import com.example.mykitchen.services.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/dishes")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class DishController {
    private final DishService fs;

    public DishController(DishService fs){
        this.fs =fs;
    }

    @GetMapping
    public List<Dish> getAll(){
        return fs.getAllDishes();
    }

    @GetMapping("/{name}")
    public Dish getFoodByName(@PathVariable String name){
        Dish f = fs.getDishByName(name);
        if(f!=null){
            return f;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"can't find this dish!");
        }
    }

    @GetMapping("/search")
    public List<Dish> find(@RequestParam String search, @RequestParam(required = false,defaultValue = "0") int time){
        return fs.SearchDishByNameTime(search,time);
    }

    @PostMapping(consumes = {"application/json"})
    public Dish addFoodJson(@RequestBody Dish f){
        return fs.saveDish(f);
    }


    @DeleteMapping
    public void deleteAll(){
        fs.deleteAll();
    }

//    @DeleteMapping("/{name}")
//    public void delete(@PathVariable String name){
//        if(!this.fs.dishExists(name)){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with name " + name + " not found!");
//        }
//         fs.deleteDishByName(name);
//    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Long id){
        if(!this.fs.dishExists(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with id " + id + " not found!");
        }
        return fs.deleteDishById(id);
    }

    @PutMapping("/{id}")
    public Dish updateFood(@PathVariable Long id, @RequestBody Dish fo){
        return this.fs.updateDish(id,fo);
    }






}
