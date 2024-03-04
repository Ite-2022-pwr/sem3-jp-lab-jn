package com.example.demo1.logic.controllers;

import com.example.demo1.logic.FileHandler;
import com.example.demo1.logic.models.Dish;
import com.example.demo1.logic.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class ShoppingController extends HomeController implements Initializable {

    @FXML
    private Button generateButton;

    @FXML
    private TextField pathInput;

    @FXML
    private HBox errorContainer;

    @FXML
    private VBox dishesScrollContainer;

    @FXML
    private Text errorMessage;

    Image thumbsInactive;
    Image thumbsActive;

    private FileHandler Fh;

    private Dish[] dishes;

    private ArrayList<Dish> selectedDishes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thumbsActive = new Image("https://www.nicepng.com/png/detail/11-116475_green-thumbs-up-png-black-and-white-stock.png");
        thumbsInactive = new Image("https://cdn.iconscout.com/icon/free/png-256/free-thumbs-69-450678.png");

        selectedDishes = new ArrayList<>();

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

        ImageView iv1 = new ImageView(thumbsInactive);  // Creative commons with attribution license for icons: No commercial usage without authorization. All rights reserved. Design (c) 2008 - Kidaubis Design http://kidaubis.deviantart.com/  http://www.kidcomic.net/ All Rights of depicted characters belong to their respective owners.
        iv1.setFitHeight(30);
        iv1.setFitWidth(30);
        iv1.setCursor(Cursor.HAND);
        iv1.getStyleClass().add("icon");
        imageHolder.getChildren().add(iv1);


        ImageView iv2 = new ImageView(thumbsActive);  // Creative commons with attribution license for icons: No commercial usage without authorization. All rights reserved. Design (c) 2008 - Kidaubis Design http://kidaubis.deviantart.com/  http://www.kidcomic.net/ All Rights of depicted characters belong to their respective owners.
        iv2.setFitHeight(30);
        iv2.setFitWidth(30);
        iv2.setCursor(Cursor.HAND);
        iv2.getStyleClass().add("icon");
        imageHolder.getChildren().add(iv2);

        iv1.setOnMouseClicked(e -> toggleSelectedDish(e, iv1, iv2, dish));
        iv2.setOnMouseClicked(e -> toggleSelectedDish(e, iv2, iv1, dish));

        iv1.getStyleClass().add("active");
        iv2.getStyleClass().add("inactive");

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

    private void toggleSelectedDish(MouseEvent e, ImageView clickedImage, ImageView otherImage, Dish dish){
        Set<String> c =  clickedImage.getStyleClass().stream().filter(style -> style.equals("active")).collect(Collectors.toSet());
        clickedImage.getStyleClass().clear();
        clickedImage.setStyle(null);
        otherImage.getStyleClass().clear();
        otherImage.setStyle(null);
        if(c.size() > 0){
            clickedImage.getStyleClass().add("inactive");
            otherImage.getStyleClass().add("active");
            selectedDishes.add(dish);
        }
        else{
            clickedImage.getStyleClass().add("active");
            otherImage.getStyleClass().add("inactive");
            selectedDishes.remove(dish);
        }
    }


    @FXML
    public void generateShoppingList(ActionEvent e) throws IOException {

        String pathToSave = pathInput.getText();

        if(pathToSave.equals("")){
            errorMessage.setText("Nie podano ścieżki zapisu zakupów");
            errorContainer.setVisible(true);
        }

        HashMap<Product.PRODUCT_CATEGORY, HashMap<String, Integer>> productsCategories = new HashMap<>();
        for(Product.PRODUCT_CATEGORY category : Product.PRODUCT_CATEGORY.values()){
            productsCategories.put(category, new HashMap<>());
        }

        for(Dish selectedDish : selectedDishes){
            for(Product product : selectedDish.getIngredients()){
                HashMap<String, Integer> productsSaved = productsCategories.get(product.getCategory());
                if(productsSaved.containsKey(product.getName())){
                    productsSaved.put(product.getName(), productsSaved.get(product.getName()) + 1);
                }
                else{
                    productsSaved.put(product.getName(), 1);
                }
                productsCategories.put(product.getCategory(), productsSaved);
            }
        }


        PDDocument pdfDocument = new PDDocument();
        PDPage firstPage = new PDPage();
        PDType0Font font = PDType0Font.load(pdfDocument, new File("c:/windows/fonts/Arial.ttf"));
        pdfDocument.addPage(firstPage);

        int offsetY = 300;
        try (PDPageContentStream contents = new PDPageContentStream(pdfDocument, firstPage)){
            for (Product.PRODUCT_CATEGORY category: productsCategories.keySet()){
//                contents.beginText();
//                contents.setFont(font, 18);
//                contents.newLineAtOffset(100, offsetY);
//                contents.showText(String.valueOf(category));
//                contents.endText();
//                offsetY+=50;
                HashMap<String, Integer> productsInCategory = productsCategories.get(category);

                for(String name: productsInCategory.keySet()){
                    contents.beginText();
                    contents.setFont(font, 12);
                    contents.newLineAtOffset(100, offsetY);
                    byte[] encodedBytes = name.getBytes(StandardCharsets.UTF_8);
                    String encodedText = new String(encodedBytes, StandardCharsets.UTF_8);
                    contents.showText(productsInCategory.get(name) + "x " + encodedText + " kategoria: " + category);
                    contents.endText();
                    offsetY+=50;
                }
                offsetY+=100;
            }
        }

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);

        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        String fileName = formattedDate + "_" + randomNumber;
        String fileExtension = ".pdf";
        String filePath = pathToSave + fileName + fileExtension;

        pdfDocument.save(filePath);
        
    }



}
