package com.example.demo.controller;

import com.example.demo.dto.ItemSaveDto;
import com.example.demo.dto.QuantityTypeDto;
import com.example.demo.dto.WarehouseDto;
import com.example.demo.handler.ProcessFinishedHandler;
import com.example.demo.rest.ItemRestClient;
import com.example.demo.rest.QuantityTypeRestClient;
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
import java.util.List;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {

    @FXML
    private BorderPane addItemBP;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField quantityTF;

    @FXML
    private ComboBox<QuantityTypeDto> quantityTypeCB;

    @FXML
    private Button saveButton;

    private WarehouseDto selectedWarehouseDto; //to pole przechowuje aktualnie wybrany magazyn
    private final ItemRestClient itemRestClient;
    private final QuantityTypeRestClient quantityTypeRestClient;

    public AddItemController() {
        itemRestClient = new ItemRestClient();
        quantityTypeRestClient = new QuantityTypeRestClient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCancelButton();
        initializeSaveButton();
    }

    private void initializeSaveButton() {
        saveButton.setOnAction(x->{
            //analogicznie jak w module employee, mozna dodac popup
            ItemSaveDto dto = new ItemSaveDto();
            String name = nameTF.getText();
            Double quantity = Double.parseDouble(quantityTF.getText());// tu mozna obsluzyc blad jesli uzytkownik wpisze cos poza liczba
            Long idQuantutyType = quantityTypeCB.getSelectionModel().getSelectedItem().getIdQuantutyType();
            Long idWarehouse = selectedWarehouseDto.getIdWarehouse();
            dto.setName(name);
            dto.setQuantity(quantity);
            dto.setIdQuantityType(idQuantutyType);
            dto.setIdWarehouse(idWarehouse);
            Thread thread = new Thread(()->{
               itemRestClient.saveItem(dto, ()->{
                   Platform.runLater(()->{
                       getStage().close();
                   });

               });
            });
            thread.start();
        });
    }

    public void loadQuantityTypes(){
        Thread thread = new Thread(()->{
            List<QuantityTypeDto> quantityTypes = quantityTypeRestClient.getQuantityTypes();
            Platform.runLater(()->{
                quantityTypeCB.setItems(FXCollections.observableArrayList(quantityTypes));
            });
        });
        thread.start();
    }
    private void initializeCancelButton() {
        cancelButton.setOnAction((x)->{
            getStage().close();
        });
    }
    private Stage getStage(){
        return (Stage) addItemBP.getScene().getWindow();
    }

    public void setWarehouseDto(WarehouseDto selectedWarehouseDto) {
        this.selectedWarehouseDto=selectedWarehouseDto;
    }
}



