package com.example.demo.controller;

import com.example.demo.dto.ItemEditViewDto;
import com.example.demo.dto.ItemSaveDto;
import com.example.demo.dto.QuantityTypeDto;
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

public class EditItemController implements Initializable {
    @FXML
    private Button cancelButton;

    @FXML
    private Button editButton;

    @FXML
    private BorderPane editItemBP;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField quantityTF;

    @FXML
    private ComboBox<QuantityTypeDto> quantityTypeCB;
    private final ItemRestClient itemRestClient;
    private Long idItemToEdit;

    public EditItemController() {
        itemRestClient = new ItemRestClient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCancel();
        initializeEditButton();

    }

    private void initializeEditButton() {
        editButton.setOnAction(x->{
            ItemSaveDto dto = new ItemSaveDto();
            String name = nameTF.getText();
            double quantity = Double.parseDouble(quantityTF.getText());
            Long idQuantityType = quantityTypeCB.getSelectionModel().getSelectedItem().getIdQuantutyType();
            dto.setIdItem(idItemToEdit);
            dto.setName(name);
            dto.setQuantity(quantity);
            dto.setIdQuantityType(idQuantityType);
            itemRestClient.saveItem(dto,()->{
                getStage().close();
            });
        });
    }

    public void loadItemData(Long idItem){ //podczas otwierania okna pobierane zostają dane przedmiotu do edycji
        Thread thread = new Thread(()->{
            ItemEditViewDto dto = itemRestClient.getEditItemData(idItem);
            Platform.runLater(()->{
                idItemToEdit=dto.getIdItem();
                nameTF.setText(dto.getName());
                quantityTF.setText(dto.getQuantity().toString());
                quantityTypeCB.setItems(FXCollections.observableArrayList(dto.getQuantityTypeDtoList()));
                for(int i=0;i< quantityTypeCB.getItems().size();i++){    //petla ustawia jako aktywny quantityType ten ktory jest przypisany do edytowanego przedmiotu
                    QuantityTypeDto quantityTypeDto = quantityTypeCB.getItems().get(i);
                    if(quantityTypeDto.getIdQuantutyType().equals(dto.getIdQuantityType())){
                        quantityTypeCB.getSelectionModel().select(i);
                    }
                }

            });
        });
        thread.start();
    }
    private void initializeCancel() {
        cancelButton.setOnAction((x)->{
            getStage().close();
        });
    }
    private Stage getStage(){
        return (Stage) editItemBP.getScene().getWindow();
    }
}


