package com.example.demo1.logic.models;

import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;

import com.example.demo1.logic.adapters.ProductAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Product {

    public enum PRODUCT_CATEGORY {
        FRUIT, VEGETABLE, MEAT
    };
    public String name;
    public int carbs;
    public int fat;
    public int protein;

    public PRODUCT_CATEGORY category;



    public Product(String name, int carbs, int fat, int protein, PRODUCT_CATEGORY category){
        this.setName(name);
        this.setCarbs(carbs);
        this.setFat(fat);
        this.setProtein(protein);
        this.setCategory(category);


    }

    public Product(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        if(carbs > 0)
            this.carbs = carbs;
        else
            throw new InvalidParameterException("Non-positive integer in carbs");
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        if(fat > 0)
            this.fat = fat;
        else
            throw new InvalidParameterException("Non-positive integer in fat");
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        if(protein > 0)
            this.protein = protein;
        else
            throw new InvalidParameterException("Non-positive integer in carbs");
    }

    public PRODUCT_CATEGORY getCategory() {
        return category;
    }

    public void setCategory(PRODUCT_CATEGORY category) {
        this.category = category;
    }







}
