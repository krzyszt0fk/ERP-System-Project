package com.example.demo.controller;

import com.example.demo.rest.ItemRestClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class viewItemController implements Initializable {

    @FXML
    private TextField nameTF;

    @FXML
    private Button okButton;

    @FXML
    private TextField quantityTF;

    @FXML
    private ComboBox<String> quantityTypeCB;

    @FXML
    private BorderPane viewItemBP;

    private final ItemRestClient itemRestClient;

    public viewItemController() {
        itemRestClient = new ItemRestClient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
