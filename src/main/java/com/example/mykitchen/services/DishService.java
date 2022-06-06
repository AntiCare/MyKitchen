package com.example.mykitchen.services;

import com.example.mykitchen.model.Dish;
import com.example.mykitchen.model.Ingredient;
import com.example.mykitchen.repositories.DishRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DishService {
    private DishRepository dishRepository;

    public DishService(DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }
    public boolean dishExists(Long id) {
        List<Dish> dishes = dishRepository.findAll();
        if (id != null) {
            for (Dish f: dishes) {
                if(Objects.equals(f.getId(), id)){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * get
     */
    public List<Dish> getAllDishes(){
        return dishRepository.findAll();
    }



    public Optional<Dish> getOneFoodById(Long id){
        return dishRepository.findById(id);
    }

    public Dish getDishByName(String name){
        List<Dish> dishes = dishRepository.findAll();
        if (name != null) {
            for (Dish f: dishes) {
                if(f.getName().equalsIgnoreCase(name)){
                   return f;
                }
            }
        }
        return null;
    }

    public List<Dish> SearchDishByNameTime(String name, int time){
        List<Dish> dishes = dishRepository.findAll();
        if (name != null) {
            List<Dish> searchDishName = new ArrayList<>();
            for (Dish f: dishes) {
                if(f.name.toLowerCase().contains(name.toLowerCase()) && f.time>time){
                    searchDishName.add(f);
                }
            }
            return searchDishName;
        }
      return null;
    }

    /**
     * add
     */

    public Dish saveDish(Dish f){
        return dishRepository.save(f);
    }

    /**
     * modify
     */
    public Dish updateDish(Long id, Dish i){
        List<Dish> dishes = dishRepository.findAll();
        if (id != null && i !=null) {
            for (Dish dish:dishes) {
                if(Objects.equals(dish.getId(), id)){
                    dish.setName(i.getName());
                    dish.setCategory(i.getCategory());
                    dish.setTime(i.getTime());
                    dish.setDescription(i.getDescription());
                    dishRepository.save(dish);
                    return dish;
                }
            }
        }

        return null;
    }

    /**
     *Delete
     */
    public Object deleteDishById(Long id){
        try {
            dishRepository.deleteById(id);
        }catch (Exception e){
            return  HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        return  HttpServletResponse.SC_OK;
    }

    public void deleteAll(){
       dishRepository.deleteAll();
    }

    public Object deleteDishByName(String name){
        List<Dish> dishes = dishRepository.findAll();
        if (name != null) {
            for (Dish f: dishes) {
                if(f.getName().equalsIgnoreCase(name)){
                    dishRepository.deleteById(f.getId());
                    return  HttpServletResponse.SC_OK;
                }
            }
        }
        return  HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }




}

