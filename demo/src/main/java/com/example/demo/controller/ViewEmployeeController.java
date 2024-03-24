package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
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

public class ViewEmployeeController implements Initializable {
    private final EmployeeRestClient employeeRestClient;
    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private Button okButton;

    @FXML
    private TextField salaryTF;

    @FXML
    private BorderPane viewEmployeeBP;

    public ViewEmployeeController() {
        employeeRestClient = new EmployeeRestClient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstNameTF.setEditable(false);//ustawione na false bo to okno ma sluzyc tylko do wyswietlania, brak mozliwosc edycji
        lastNameTF.setEditable(false);
        salaryTF.setEditable(false);
        initializeOkButton();
    }

   // metoda wczytujaca dane pracownika
    public void loadEmployeeData(Long idEmployee, EmployeeLoadedHandler handler){
        //wysylamy rzadanie do serwera (endpoint employyee/idEmployee)  i wczytywali dane urzytkownika z okreslonym id
        Thread thread = new Thread(()->{
            EmployeeDto dto = employeeRestClient.getEmployee(idEmployee);
            Platform.runLater(()->{//nie powinno sie aktualizowac widoku z osobnego watku, trzeba to robic przez metode platform
                firstNameTF.setText(dto.getFirstName());
                lastNameTF.setText(dto.getLastName());
                salaryTF.setText(dto.getSalary());
                handler.handle();
            });
        });
        thread.start();
    }


    private void initializeOkButton() {
        okButton.setOnAction(x->{
            getStage().close();
        });
    }

    private Stage getStage(){
        return (Stage) viewEmployeeBP.getScene().getWindow();
    }
}

