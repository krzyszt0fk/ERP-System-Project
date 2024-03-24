package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.factory.PopupFactory;
import com.example.demo.handler.EmployeeLoadedHandler;
import com.example.demo.rest.EmployeeRestClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable {
    private final EmployeeRestClient employeeRestClient;
    private final PopupFactory popupFactory;
    private Long idEmployee;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editButton;

    @FXML
    private BorderPane editEmployeeBP;

    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private TextField salaryTF;

    public EditEmployeeController() {
        employeeRestClient = new EmployeeRestClient();
        popupFactory = new PopupFactory();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCancel();
        initializeEditButton();
    }

    private void initializeEditButton() {
        editButton.setOnAction((x->{
            Stage waintingPopup = popupFactory.createWaintingPopup("Connecting to the server...");
            waintingPopup.show();
            Thread thread = new Thread(()->{
                //metoda zwracajaca EmployeeDto
                EmployeeDto dto= createEmployeeDto();
                employeeRestClient.saveEmployee(dto,()->{
                    Platform.runLater(()->{
                        waintingPopup.close();
                        Stage infoPopup = popupFactory.createInfoPopup("Employee " + idEmployee + " has been updated correctly", () -> {
                            getStage().close();// ta lambda wykonuje sie kiedy uzytkownik wcisnie OK
                        });
                        infoPopup.show();

                    });
                        });
            });
            thread.start();
        }));
    }

    private EmployeeDto createEmployeeDto() {//mrtoda zwracajaca obiekt klasy EmployeeDto
        String firstName=firstNameTF.getText();
        String lastName=lastNameTF.getText();
        String salary=salaryTF.getText();
        EmployeeDto dto = new EmployeeDto();
        dto.setIdEmployee(idEmployee);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setSalary(salary);
        return dto;
    }
    public void loadEmployeeData(Long idEmployee, EmployeeLoadedHandler handler){
        //wysylamy rzadanie do serwera (endpoint employyee/idEmployee)  i wczytywali dane urzytkownika z okreslonym id
        Thread thread = new Thread(()->{
            EmployeeDto dto = employeeRestClient.getEmployee(idEmployee);
            Platform.runLater(()->{//nie powinno sie aktualizowac widoku z osobnego watku, trzeba to robic przez metode platform
                this.idEmployee=idEmployee;
                firstNameTF.setText(dto.getFirstName());
                lastNameTF.setText(dto.getLastName());
                salaryTF.setText(dto.getSalary());
                handler.handle();
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
        return (Stage) editEmployeeBP.getScene().getWindow();
    }
}

