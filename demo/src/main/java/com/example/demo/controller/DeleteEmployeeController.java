package com.example.demo.controller;

import com.example.demo.factory.PopupFactory;
import com.example.demo.rest.EmployeeRestClient;
import com.example.demo.table.EmployeeTableModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class DeleteEmployeeController implements Initializable {
    private final EmployeeRestClient employeeRestClient;
    private final PopupFactory popupFactory;
    private Long idEmployee;
    @FXML
    private Button cancelButton;

    @FXML
    private Button deleteButton;

    @FXML
    private BorderPane deleteEmployeeBP;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    public DeleteEmployeeController() {
        popupFactory = new PopupFactory();
        employeeRestClient = new EmployeeRestClient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCancel();
        initializeDelete();
        
    }
    public void loadEmployeeData(EmployeeTableModel employeeTableModel){
                this.idEmployee=employeeTableModel.getIdEmployee();
                firstNameLabel.setText(employeeTableModel.getFirstName());
                lastNameLabel.setText(employeeTableModel.getLastName());
    }

    private void initializeDelete() {
        deleteButton.setOnAction(x->{
            Stage waintingPopup = popupFactory.createWaintingPopup("Deleting employee...");
            waintingPopup.show();
            Thread thread = new Thread(()->{
                employeeRestClient.deleteEmployee(idEmployee,()->{
                    //co ma sie wydarzyc kiedy pracownik zostanie usuniety
                    Platform.runLater(()->{
                        waintingPopup.close();
                        Stage infoPopup = popupFactory.createInfoPopup("Employee has been deleted", () -> {
                            //co ma sie wydarzyc kiedy wcisniety zostanie przycisk OK
                            getStage().close();
                    });
                        infoPopup.show();
                    });


                });
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
        return (Stage) deleteEmployeeBP.getScene().getWindow();
    }
}
