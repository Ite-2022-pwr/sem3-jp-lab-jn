module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires org.apache.pdfbox;


    opens com.example.demo1 to javafx.fxml, com.google.gson;
    exports com.example.demo1;

    exports com.example.demo1.logic.controllers;
    opens com.example.demo1.logic.controllers to com.google.gson, javafx.fxml;
    opens com.example.demo1.logic.models to com.google.gson;
    opens com.example.demo1.logic to com.google.gson;
}