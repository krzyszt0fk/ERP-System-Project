package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.factory.PopupFactory;
import com.example.demo.rest.EmployeeRestClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {
    private final PopupFactory popupFactory;
    private final EmployeeRestClient employeeRestClient;

    @FXML
    private Button cancelButton;
    @FXML
    private BorderPane addEmployeeBP;

    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private TextField salaryTF;

    @FXML
    private Button saveButton;

    public AddEmployeeController() {
        popupFactory = new PopupFactory();
        employeeRestClient = new EmployeeRestClient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCancel();
        initializeSave();
    }

    private void initializeSave() {
        saveButton.setOnAction((x)->{
            EmployeeDto dto=createEmployeeDto();
            Stage waintingPopup = popupFactory.createWaintingPopup("Connecting to the server.....");
            waintingPopup.show();
            employeeRestClient.saveEmployee(dto,()->{//lambda okrsla co ma sie dziac jak dto sie zapisze w bazie danych
                waintingPopup.close();
                Stage info = popupFactory.createInfoPopup("Employee has been saved!", () -> {
                    getStage().close();
                });
                info.show();
            });

        });
    }

    private EmployeeDto createEmployeeDto() {//mrtoda zwracajaca obiekt klasy EmployeeDto
            String firstName=firstNameTF.getText();
            String lastName=lastNameTF.getText();
            String salary=salaryTF.getText();
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setSalary(salary);
        return dto;
    }

    private void initializeCancel() {
        cancelButton.setOnAction((x)->{
            getStage().close();
        });
    }
    private Stage getStage(){
        return (Stage) addEmployeeBP.getScene().getWindow();
    }
}
