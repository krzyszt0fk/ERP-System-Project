package com.example.demo.controller;

import com.example.demo.dto.ItemDto;
import com.example.demo.rest.ItemRestClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewItemController implements Initializable {

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

    public ViewItemController() {
        itemRestClient = new ItemRestClient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeControls();
        initializeOkButton();
    }

    public void loadItemData(Long idItem) { //id przedmiotu ktorefo dane chcemy wyswietlic
        Thread thread = new Thread(()->{
            ItemDto itemDto= itemRestClient.getItem(idItem);
            Platform.runLater(()->{
                nameTF.setText(itemDto.getName());
                quantityTF.setText(itemDto.getQuantity().toString());
                quantityTypeCB.setItems(FXCollections.observableArrayList(itemDto.getQuantityType()));
                quantityTypeCB.getSelectionModel().select(0);
            });

        });
        thread.start();
    }
    private void initializeControls() {
        nameTF.setEditable(false);
        quantityTF.setEditable(false);
        quantityTypeCB.setEditable(false);
    }

    private void initializeOkButton() {
        okButton.setOnAction(x->{
            getStage().close();
        });
    }

    private Stage getStage(){
        return (Stage) viewItemBP.getScene().getWindow();
    }
}

