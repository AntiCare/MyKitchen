package com.example.mykitchen.services;

import com.example.mykitchen.model.Dish;
import com.example.mykitchen.model.Ingredient;
import com.example.mykitchen.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IngredientsService {
    private IngredientRepository ir ;

    public IngredientsService(IngredientRepository ir){
        this.ir = ir;
    }
    public boolean ingredientExists(Long id) {
        List<Ingredient> ingredients = ir.findAll();
        if (id != null) {
            for (Ingredient i: ingredients) {
                if(Objects.equals(i.getId(), id)){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * get
     */
    public List<Ingredient> getAllIngredients(){
        return  ir.findAll();
    }



    public Optional<Ingredient> getOneIngredientById(Long id){
        return ir.findById(id);
    }

    public Ingredient getIngredientByName(String name){
        List<Ingredient> ingredients = ir.findAll();
        if (name != null) {
            for (Ingredient i: ingredients) {
                if(i.getName().equalsIgnoreCase(name)){
                    return i;
                }
            }
        }
        return null;
    }

    public List<Ingredient> SearchIngredientByNameWeight(String name, int weight){
        List<Ingredient> ingredients = ir.findAll();
        if (name != null) {
            List<Ingredient> searchIngredientName = new ArrayList<>();
            for (Ingredient i:ingredients) {
                if(i.name.toLowerCase().contains(name.toLowerCase()) && i.weight>weight){
                    searchIngredientName.add(i);
                }
            }
            return searchIngredientName;
        }
        return null;
    }

    /**
     * add
     */

    public Ingredient saveIngredient(Ingredient i){
        return ir.save(i);
    }

    /**
     * modify
     */
    public Ingredient updateIngredient(Long id, Ingredient i){
        List<Ingredient> ingredients = ir.findAll();
        if (id != null && i !=null) {
            for (Ingredient in:ingredients) {
                if(Objects.equals(in.getId(), id)){
                   in.setName(i.getName());
                   in.setWeight(i.getWeight());
                   in.setDescription(i.getDescription());
                   ir.save(in);
                   return in;
                }
            }
        }

        return null;
    }

    /**
     *Delete
     */
    public Object deleteIngredientById(Long id){
        try {
            ir.deleteById(id);
        }catch (Exception e){
            return  HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        return  HttpServletResponse.SC_OK;
    }

    public void deleteAll(){
        ir.deleteAll();
    }

    public void deleteIngredientByName(String name){
        List<Ingredient> ingredients = ir.findAll();
        if (name != null) {
            for (Ingredient i:ingredients) {
                if(i.getName().equalsIgnoreCase(name)){
                    ir.deleteById(i.getId());
                }
            }
        }
    }




}
