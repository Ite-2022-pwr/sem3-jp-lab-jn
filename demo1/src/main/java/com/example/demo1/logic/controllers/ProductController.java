package com.example.demo1.logic.controllers;

import com.example.demo1.logic.FileHandler;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductController extends HomeController implements Initializable {
    @FXML
    VBox productScrollContainer;

    Product[] products;

    FileHandler Fh;

    Image editIcon;
    Image deleteIcon;

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

        ImageView iv1 = new ImageView(editIcon);  // Creative commons with attribution license for icons: No commercial usage without authorization. All rights reserved. Design (c) 2008 - Kidaubis Design http://kidaubis.deviantart.com/  http://www.kidcomic.net/ All Rights of depicted characters belong to their respective owners.
        iv1.setFitHeight(20);
        iv1.setFitWidth(20);
        iv1.getStyleClass().add("icon");
        iv1.setCursor(Cursor.HAND);
        iv1.setOnMouseClicked(e -> editProductPageOnClick(e, product));
        productCard.getChildren().add(iv1);

        ImageView iv2 = new ImageView(deleteIcon);  // Creative commons with attribution license for icons: No commercial usage without authorization. All rights reserved. Design (c) 2008 - Kidaubis Design http://kidaubis.deviantart.com/  http://www.kidcomic.net/ All Rights of depicted characters belong to their respective owners.
        iv2.setFitHeight(20);
        iv2.setFitWidth(20);
        iv2.setCursor(Cursor.HAND);
        iv2.getStyleClass().add("icon");
        iv2.setOnMouseClicked(e -> deleteProductOnClick(product.getName()));
        productCard.getChildren().add(iv2);
        return productCard;

    }


    private void deleteProductOnClick(String productNameToDelete){

        products = Arrays.stream(products).sequential()
                .filter(product -> !(product.getName().equals(productNameToDelete))).distinct().toArray(Product[]::new);

        try{
            Fh.writeProductFile(products);
            productScrollContainer.getChildren().clear();
            populateFXMLPage();

        }
        catch (IOException e){
            System.out.println("could write products to file");
        }

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

    @FXML
    public void addNewProductPageOnClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/add-product.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();

        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/demo1/css/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void editProductPageOnClick(MouseEvent e, Product product){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/edit-product.fxml"));
            EditProductController.product = product;
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
