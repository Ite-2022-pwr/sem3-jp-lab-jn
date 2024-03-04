package com.example.demo1.logic;

import com.example.demo1.logic.adapters.ProductAdapter;
import com.example.demo1.logic.models.Dish;
import com.example.demo1.logic.models.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private String productsAbsPath;
    private String dishesAbsPath;

    public String getProductsAbsPath() {
        return productsAbsPath;
    }

    public void setProductsAbsPath(String productsAbsPath) throws IOException {
        this.createFile(productsAbsPath);
        this.productsAbsPath = productsAbsPath;
    }

    public String getDishesAbsPath() {
        return dishesAbsPath;
    }

    public void setDishesAbsPath(String dishesAbsPath) throws IOException {
        this.createFile(dishesAbsPath);
        this.dishesAbsPath = dishesAbsPath;
    }

    public FileHandler(String productsAbsPath, String dishesAbsPath) throws IOException {

        if(productsAbsPath.equals("") || dishesAbsPath.equals("")){
            this.setProductsAbsPath(System.getProperty("user.dir") + File.separator + "products.json");
            this.setDishesAbsPath(System.getProperty("user.dir") + File.separator + "dishes.json");
        }
        else{
            this.setProductsAbsPath(productsAbsPath);
            this.setDishesAbsPath(dishesAbsPath);
        }
    }

    public FileHandler() throws IOException {
        this.setProductsAbsPath(System.getProperty("user.dir") + File.separator + "products.json");
        this.setDishesAbsPath(System.getProperty("user.dir") + File.separator + "dishes.json");
    }

    public Product[] readProductFile() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(this.productsAbsPath));
        Gson gson = new Gson();
        Product[] t = gson.fromJson(reader, Product[].class);

        if(t == null){
            return new Product[]{};
        }
        return t;
    }

    public Dish[] readDishesFile() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(this.dishesAbsPath));
        Gson gson = new Gson();
        Dish[] t = gson.fromJson(reader, Dish[].class);

        if(t == null){
            return new Dish[]{};
        }
        return t;
    }




    public void writeProductFile(Product[] products) throws IOException {

        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter(this.productsAbsPath, false);
        gson.toJson(products, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void writeDishFile(Dish[] dishes) throws IOException {

        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter(this.dishesAbsPath, false);
        gson.toJson(dishes, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void appendProductFile(Product product) throws IOException {

        ArrayList<Product> products = new ArrayList<Product>(List.of(readProductFile()));

        products.removeIf(product1 -> product1.getName().equals(product.getName()));
        products.add(product);
        Gson gson = new Gson();
        Product[] productsList = products.toArray(new Product[0]);
        FileWriter fileWriter = new FileWriter(this.productsAbsPath, false);
        gson.toJson(productsList, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void appendDishFile(Dish dish) throws IOException{
        ArrayList<Dish> dishes = new ArrayList<Dish>(List.of(readDishesFile()));

        dishes.removeIf(dish1 -> dish1.getName().equals(dish.getName()));
        dishes.add(dish);
        Gson gson = new Gson();
        Dish[] dishesList = dishes.toArray(new Dish[0]);
        FileWriter fileWriter = new FileWriter(this.dishesAbsPath, false);
        gson.toJson(dishesList, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public final void createFile(String path) throws IOException {

        File myObj = new File(path);
        if (myObj.createNewFile())
            System.out.println("File created: " + path);
    }

}
