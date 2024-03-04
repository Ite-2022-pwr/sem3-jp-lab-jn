package com.example.demo1.logic.models;

import java.util.ArrayList;

public class Dish {


    private String name;

    private ArrayList<Product> ingredients;

    private DISH_CATEGORY category;

    public enum DISH_CATEGORY {
        BREAKFAST, LUNCH, DINNER, SUPPER
    }

    public Dish(String name, DISH_CATEGORY category, ArrayList<Product> ingredients){
        this.setName(name);
        this.setCategory(category);
        this.setIngredients(ingredients);
    }

    public ArrayList<Product> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Product> ingredients) {
        this.ingredients = ingredients;
    }

    public DISH_CATEGORY getCategory() {
        return category;
    }

    public void setCategory(DISH_CATEGORY category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int calculateCalories(){
        int calories = 0;

        for(Product product : ingredients){
            calories+= (product.getCarbs() * 4 + product.getFat() * 9 + product.getProtein() * 4);
        }

        return calories;

    }

    public void addIngredient(Product product){
        this.ingredients.add(product);
    }

    public void removeIngredient(Product product){
        for(Product product1 : this.ingredients){
            if(product1.getName().equals(product.getName())){
                this.ingredients.remove(product1);
                break;
            }
        }
    }



}
