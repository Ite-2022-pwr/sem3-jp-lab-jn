package com.example.demo1.logic.controllers;

import com.example.demo1.logic.FileHandler;
import com.example.demo1.logic.models.Dish;
import com.example.demo1.logic.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class DishController extends HomeController implements Initializable {

    @FXML
    private VBox dishesScrollContainer;
    private Image editIcon;
    private Image deleteIcon;
    FileHandler Fh;
    Dish[] dishes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        editIcon = new Image("https://icons.iconarchive.com/icons/custom-icon-design/mono-general-2/256/edit-icon.png");
        deleteIcon = new Image("https://icons.iconarchive.com/icons/github/octicons/256/trash-24-icon.png");

        try {
            Fh = new FileHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            dishes = Fh.readDishesFile();
            populateFXMLPage();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void populateFXMLPage(){
        for(Dish dish : dishes){
            try {
                dishesScrollContainer.getChildren().add(makeFXMLDishCard(dish));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private VBox makeFXMLDishCard(Dish dish) throws IllegalAccessException {

        VBox dishCard = new VBox(0);
        dishCard.getStyleClass().add("product-div");
        dishCard.setAlignment(Pos.TOP_LEFT);
        VBox.setMargin(dishCard,new Insets(20,0,0,0));

        HBox topContainer = new HBox();
        topContainer.setPadding(new Insets(0,0,0,0));
        topContainer.setAlignment(Pos.CENTER_LEFT);

        Text dishTitle = new Text();
        dishTitle.setText(dish.getName());
        dishTitle.setFont(new Font(19));
        topContainer.getChildren().add(dishTitle);



        HBox imageHolder = new HBox();
        HBox.setMargin(imageHolder, new Insets(0,0,0,450));

        ImageView iv1 = new ImageView(editIcon);  // Creative commons with attribution license for icons: No commercial usage without authorization. All rights reserved. Design (c) 2008 - Kidaubis Design http://kidaubis.deviantart.com/  http://www.kidcomic.net/ All Rights of depicted characters belong to their respective owners.
        iv1.setFitHeight(20);
        iv1.setFitWidth(20);
        iv1.setCursor(Cursor.HAND);
        iv1.setOnMouseClicked(e -> editDishPageOnClick(e, dish));
        iv1.getStyleClass().add("icon");
        imageHolder.getChildren().add(iv1);

        ImageView iv2 = new ImageView(deleteIcon);  // Creative commons with attribution license for icons: No commercial usage without authorization. All rights reserved. Design (c) 2008 - Kidaubis Design http://kidaubis.deviantart.com/  http://www.kidcomic.net/ All Rights of depicted characters belong to their respective owners.
        iv2.setFitHeight(20);
        iv2.setFitWidth(20);
        iv2.setCursor(Cursor.HAND);
        iv2.setOnMouseClicked(e -> deleteProductOnClick(dish.getName()));
        iv2.getStyleClass().add("icon");
        imageHolder.getChildren().add(iv2);

        topContainer.getChildren().add(imageHolder);
        dishCard.getChildren().add(topContainer);

        Text CategoryAndCalories = new Text();
        CategoryAndCalories.setText(dish.getCategory() + " " + dish.calculateCalories() + "kcal");


        dishCard.getChildren().add(CategoryAndCalories);

        FlowPane productContainer = new FlowPane();
        productContainer.setPadding(new Insets(20,20,0,0));
        for(Product product : dish.getIngredients()){
            Text productText = new Text();
            HBox container = new HBox(0);
            productText.setText(product.getName());
            container.setPadding(new Insets(0,0,0,30));
            container.getChildren().add(productText);
            productContainer.getChildren().add(container);
        }

        dishCard.getChildren().add(productContainer);

        return dishCard;

    }

    @FXML
    public void addNewDishPageOnClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/add-dish.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();

        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/demo1/css/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void deleteProductOnClick(String dishNameToDelete){

        dishes = Arrays.stream(dishes).sequential()
                .filter(dish -> !(dish.getName().equals(dishNameToDelete))).distinct().toArray(Dish[]::new);

        try{
            Fh.writeDishFile(dishes);
            dishesScrollContainer.getChildren().clear();
            populateFXMLPage();

        }
        catch (IOException e){
            System.out.println("could not write dishes to file");
        }

    }

    @FXML
    public void editDishPageOnClick(MouseEvent e, Dish dish){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/edit-dish.fxml"));
            EditDishController.dish = dish;
            Parent root = loader.load();

            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/demo1/css/styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException er){
            System.out.println(er);
        }
    }

}
