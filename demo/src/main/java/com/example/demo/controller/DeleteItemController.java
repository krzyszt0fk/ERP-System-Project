package com.example.demo.controller;

import com.example.demo.rest.ItemRestClient;
import com.example.demo.table.ItemTableModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteItemController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button deleteButton;

    @FXML
    private BorderPane deleteItemBP;

    @FXML
    private Label nameLabel;

    @FXML
    private Label quantityLabel;
    private final ItemRestClient itemRestClient;
    private Long idItemToDelete;

    public DeleteItemController() {
        itemRestClient = new ItemRestClient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCancel();
        initializeDeleteButton();

    }
    public void loadItem(ItemTableModel item){
        nameLabel.setText(item.getName());
        quantityLabel.setText(item.getQuantity()+" "+item.getQuantityType());
        this.idItemToDelete = item.getIdItem();
    }

    private void initializeDeleteButton() {
        deleteButton.setOnAction(x->{
            Thread thread = new Thread(()->{
                itemRestClient.deleteItem(idItemToDelete);
                Platform.runLater(()->getStage().close()); //dodac Popup z informacja czy udalo sie usunac
            });
            thread.start();
        });
    }

    private void initializeCancel() {
        cancelButton.setOnAction((x)->{
            getStage().close();
        });
    }
    private Stage getStage(){
        return (Stage) deleteItemBP.getScene().getWindow();
    }
}
