package com.example.demo1.logic.validators;

import com.example.demo1.logic.FileHandler;
import com.example.demo1.logic.models.Dish;
import com.example.demo1.logic.models.Product;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public class DishValidator {

    public static Dish parse(String name, Dish.DISH_CATEGORY category, ArrayList<Product> ingredients, FileHandler Fh, boolean validateUniqueName) throws ParseException, FileNotFoundException {

        if(name.equals("")){
            throw new ParseException("Nie podano nazwy posiłku", 0);
        }

        if(validateUniqueName){
            Dish[] dishes = Fh.readDishesFile();
            for(Dish dish : dishes){
                if(dish.getName().equals(name)){
                    throw new ParseException("Taki posiłek już istnieje, podaj inną nazwę",0);
                }
            }
        }

        return new Dish(name, category, ingredients);

    }
}
