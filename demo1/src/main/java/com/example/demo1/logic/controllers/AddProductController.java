package com.example.demo1.logic.controllers;

import com.example.demo1.logic.FileHandler;
import com.example.demo1.logic.adapters.ProductAdapter;
import com.example.demo1.logic.models.Product;
import com.example.demo1.logic.validators.ProductValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class AddProductController extends HomeController  implements Initializable {


    @FXML
    private ChoiceBox<Product.PRODUCT_CATEGORY> categorySelect;

    @FXML
    private TextField nameInput;
    @FXML
    private TextField fatInput;
    @FXML
    private TextField proteinInput;

    @FXML
    private TextField carbsInput;

    @FXML
    private HBox errorContainer;
    @FXML
    private Label errorMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        categorySelect.getItems().addAll(Product.PRODUCT_CATEGORY.values());
        categorySelect.setValue(Product.PRODUCT_CATEGORY.VEGETABLE);

    }


    @FXML
    public void submitForm() throws IOException {

        String name = nameInput.getCharacters().toString();
        String fat = fatInput.getCharacters().toString();
        String protein = proteinInput.getCharacters().toString();
        String carbs = carbsInput.getCharacters().toString();
        Product.PRODUCT_CATEGORY category = categorySelect.getValue();


        try{
            FileHandler Fh = new FileHandler();
            Product p = ProductValidator.parse(fat,protein,carbs,name,category, Fh);
            Fh.appendProductFile(p);
            errorMessage.setText("Dodano nowy produkt!");
            errorContainer.setStyle("-fx-background-color: green");
            errorContainer.setVisible(true);
        }
        catch (ParseException e){
            errorMessage.setText(e.getMessage());
            errorContainer.setVisible(true);
        }
        catch(IOException e){
            errorMessage.setText("Nie udało się dodać nowego produktu");
            errorContainer.setVisible(true);
        }
    }

}
