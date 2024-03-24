package com.example.demo.controller;

//app controller obsluguje app.fxml

import com.example.demo.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {


    private final static String employee_module_view ="/com/example/demo/employee.fxml";
    private final static String warehouse_module_view ="/com/example/demo/warehouse.fxml";
    private final static String login_view ="/com/example/demo/login.fxml";
    @FXML
    private Pane appPane;

    @FXML
    private BorderPane appBP;

    @FXML
    private MenuItem employeeModuleMI;

    @FXML
    private MenuItem exitModuleMI;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private MenuItem warehouseModuleMI;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDefaultView();
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        warehouseModuleMI.setOnAction(x-> loadModuleView(warehouse_module_view));//podmienia widok wstrzykiwany do appPane
        employeeModuleMI.setOnAction(x->loadModuleView(employee_module_view));
        logoutMenuItem.setOnAction(x->logout());//metoda logout zamuka aktualne okno i otwiera okno logowania
        exitModuleMI.setOnAction(x->getStage().close());
    }

    private void logout() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(login_view));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, HelloApplication.WIDTH,HelloApplication.HEIGHT));
            stage.show();
            getStage().close();
        } catch (IOException e) {
            throw new RuntimeException("Can't load fxml file:\"");
        }
    }

    private void loadDefaultView() {
        loadModuleView(employee_module_view);
    }
    private void loadModuleView(String viewPath){
        appPane.getChildren().clear();//czyscimy bo bedziemy zmieniali widok do appPane
        try {
            BorderPane borderPane= FXMLLoader.load(getClass().getResource(viewPath));
            appPane.getChildren().add(borderPane);
        } catch (IOException e) {
            throw new RuntimeException("Can't load fxml file:" +login_view);
        }


    }

    private Stage getStage(){
        return (Stage) appBP.getScene().getWindow();
    }
}



