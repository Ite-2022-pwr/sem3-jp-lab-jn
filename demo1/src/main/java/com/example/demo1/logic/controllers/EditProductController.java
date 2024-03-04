package com.example.demo1.logic.controllers;

import com.example.demo1.logic.FileHandler;
import com.example.demo1.logic.models.Product;
import com.example.demo1.logic.validators.ProductValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class EditProductController extends HomeController implements Initializable {

    @FXML
    private Button editButton;

    @FXML
    private ChoiceBox<Product.PRODUCT_CATEGORY> categorySelect;


    public static Product product;

    @FXML
    TextField nameInput1;
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
        nameInput1.setText(product.getName());
        fatInput.setText(String.valueOf(product.getFat()));
        proteinInput.setText(String.valueOf(product.getProtein()));
        carbsInput.setText(String.valueOf(product.getCarbs()));
        categorySelect.getItems().addAll(Product.PRODUCT_CATEGORY.values());
        categorySelect.setValue(product.getCategory());

    }


    @FXML
    public void submitForm() throws IOException {

        String fat = fatInput.getCharacters().toString();
        String protein = proteinInput.getCharacters().toString();
        String carbs = carbsInput.getCharacters().toString();
        Product.PRODUCT_CATEGORY category = categorySelect.getValue();


        try{
            FileHandler Fh = new FileHandler();
            Product p = ProductValidator.parse(fat,protein,carbs,product.name,category, Fh, false);
            Fh.appendProductFile(p);
            errorMessage.setText("Edytowano produkt!");
            errorContainer.setStyle("-fx-background-color: green");
            errorContainer.setVisible(true);
        }
        catch (ParseException e){
            errorMessage.setText(e.getMessage());
            errorContainer.setVisible(true);
        }
        catch(IOException e){
            errorMessage.setText("Nie udało się zedytować produktu");
            errorContainer.setVisible(true);
        }

    }
}
