package com.example.demo1.logic.controllers;

import com.example.demo1.logic.FileHandler;
import com.example.demo1.logic.models.Dish;
import com.example.demo1.logic.models.Product;
import com.example.demo1.logic.validators.DishValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class AddDishController extends HomeController implements Initializable {

    @FXML
    VBox productScrollContainer;

    @FXML
    private HBox errorContainer;
    @FXML
    private Text errorMessage1;

    @FXML
    private TextField nameInput;

    @FXML
    private ChoiceBox<Dish.DISH_CATEGORY> categorySelect;

    Product[] products;

    ArrayList<Product> selectedProducts;

    FileHandler Fh;

    Image thumbsInactive;
    Image thumbsActive;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        thumbsActive = new Image("https://www.nicepng.com/png/detail/11-116475_green-thumbs-up-png-black-and-white-stock.png");
        thumbsInactive = new Image("https://cdn.iconscout.com/icon/free/png-256/free-thumbs-69-450678.png");
        selectedProducts = new ArrayList<>();

        categorySelect.getItems().addAll(Dish.DISH_CATEGORY.values());
        categorySelect.setValue(Dish.DISH_CATEGORY.BREAKFAST);

        try {
            Fh = new FileHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            products = Fh.readProductFile();
            populateFXMLPage();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private HBox makeFXMLProductCard(Product product) throws IllegalAccessException {



        HBox productCard = new HBox(0);
        productCard.getStyleClass().add("product-div");
        productCard.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(productCard,new Insets(0,20,20,0));



        Field[] fields = product.getClass().getFields();

        for(Field field : fields){
            Label fieldLabel = new Label(field.get(product).toString());
            fieldLabel.setFont(new Font(16));
            fieldLabel.setPadding(new Insets(0,70,0,0));
            productCard.getChildren().add(fieldLabel);
        }


        ImageView iv1 = new ImageView(thumbsInactive);
        ImageView iv2 = new ImageView(thumbsActive);
        iv1.setFitHeight(30);
        iv1.setFitWidth(30);
        iv1.getStyleClass().add("icon");
        iv1.getStyleClass().add("active");
        iv1.setCursor(Cursor.HAND);
        iv1.setOnMouseClicked(e -> toggleSelectedProduct(e, iv1, iv2,product));

        iv2.setFitHeight(30);
        iv2.setFitWidth(30);
        iv2.getStyleClass().add("icon");
        iv2.getStyleClass().add("inactive");
        iv2.setCursor(Cursor.HAND);
        iv2.setOnMouseClicked(e -> toggleSelectedProduct(e, iv2, iv1, product));

        productCard.getChildren().add(iv1);
        productCard.getChildren().add(iv2);

        return productCard;

    }

    private void populateFXMLPage(){
        for(Product product : products){
            try {
                productScrollContainer.getChildren().add(makeFXMLProductCard(product));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void toggleSelectedProduct(MouseEvent e, ImageView clickedImage, ImageView otherImage, Product product){
       Set<String> c =  clickedImage.getStyleClass().stream().filter(style -> style.equals("active")).collect(Collectors.toSet());
        clickedImage.getStyleClass().clear();
        clickedImage.setStyle(null);
        otherImage.getStyleClass().clear();
        otherImage.setStyle(null);
       if(c.size() > 0){
           clickedImage.getStyleClass().add("inactive");
           otherImage.getStyleClass().add("active");
           selectedProducts.add(product);
       }
       else{
           clickedImage.getStyleClass().add("active");
           otherImage.getStyleClass().add("inactive");
           selectedProducts.remove(product);
       }
    }

    @FXML
    private void submitForm() throws FileNotFoundException {
        String name = nameInput.getCharacters().toString();
        Dish.DISH_CATEGORY category = categorySelect.getValue();

        try{
            Dish d = DishValidator.parse(name, category, selectedProducts, Fh, true);
            Fh.appendDishFile(d);
            errorMessage1.setText("Dodano nowy posiłek!");
            errorContainer.setStyle("-fx-background-color: green");
            errorContainer.setVisible(true);
        } catch(IOException e){
            errorMessage1.setText("Nie udało się dodać nowego posiłku");
            errorContainer.setVisible(true);
        } catch (ParseException e) {
            errorMessage1.setText(e.getMessage());
            errorContainer.setVisible(true);
        }
    }

}
