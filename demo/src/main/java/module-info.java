module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.demo to javafx.graphics;
    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo.table to javafx.base;
    exports com.example.demo.controller;
    requires static lombok;
    requires spring.boot;
    requires spring.web;
    exports com.example.demo.dto;

}