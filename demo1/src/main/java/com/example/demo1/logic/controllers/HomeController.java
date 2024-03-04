package com.example.demo1.logic.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HomeController {

    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    public void homePageOnClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/home.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/demo1/css/styles.css")).toExternalForm());
        stage.show();
    }
    @FXML
    public void productPageOnClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/products.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();

        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/demo1/css/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void dishPageOnClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/dishes.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/demo1/css/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void shoppingPageOnClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo1/shopping.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/demo1/css/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }








}
