package com.example.mykitchen.services;

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
    /**
     * Check if ingredient exist by id
     * @param id ingredient id
     * @return bool
     */
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
     * GET - get all ingredients
     * @return List<Ingredient>
     */
    public List<Ingredient> getAllIngredients(){
        return  ir.findAll();
    }


    /**
     * GET - find ingredient by id
     * @param id ingredient id
     * @return Optional<Ingredient>
     */
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
    /**
     * GET - search ingredient by name
     * @param name ingredient name
     * @return List<Ingredient> or null
     */
    public List<Ingredient> SearchIngredientByNameWeight(String name){
        List<Ingredient> ingredients = ir.findAll();
        if (name != null) {
            List<Ingredient> searchIngredientName = new ArrayList<>();
            for (Ingredient i:ingredients) {
                if(i.name.toLowerCase().contains(name.toLowerCase())){
                    searchIngredientName.add(i);
                }
            }
            return searchIngredientName;
        }
        return null;
    }

    /**
     * POST - add new ingredient.
     * @param i new ingredient
     * @return HTTP status 201 or 500
     */
    public Object saveIngredient(Ingredient i){
        try {
            ir.save(i);
            return HttpServletResponse.SC_CREATED;
        }catch (Exception e){
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * PUT - modify ingredient by id
     * @param id ingredient id
     * @param i new ingredient
     * @return HTTP status 201 or 500
     */
    public Object updateIngredient(Long id, Ingredient i){
        List<Ingredient> ingredients = ir.findAll();
        if (id != null && i !=null) {
            for (Ingredient in:ingredients) {
                if(Objects.equals(in.getId(), id)){
                   in.setName(i.getName());
                   in.setWeight(i.getWeight());
                   in.setDescription(i.getDescription());
                   ir.save(in);
                    return HttpServletResponse.SC_CREATED;
                }
            }
        }
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    /**
     * DELETE - delete ingredient by id.
     * @param id ingredient id
     * @return HTTP status 200 or 500
     */
    public Object deleteIngredientById(Long id){
        try {
            ir.deleteById(id);
        }catch (Exception e){
            return  HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        return  HttpServletResponse.SC_OK;
    }

    /**
     * DELETE - delete all ingredients
     */
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
