package com.example.demo.controller;

//app controller obsluguje app.fxml

import com.example.demo.dto.EmployeeDto;
import com.example.demo.factory.PopupFactory;
import com.example.demo.rest.EmployeeRestClient;
import com.example.demo.table.EmployeeTableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EmployeeController implements Initializable {

    private final EmployeeRestClient employeeRestClient;
    private static final String addEmployee_FXML = "/com/example/demo/addEmployee.fxml";
    private static final String viewEmployee_FXML = "/com/example/demo/viewEmployee.fxml";
    private static final String editEmployee_FXML = "/com/example/demo/editEmployee.fxml";
    private static final String deleteEmployee_FXML = "/com/example/demo/deleteEmployee.fxml";

    private final PopupFactory popupFactory;


    //w przypadku Tabel JavaFx dane ktore przekazujemy do tabeli musza znajdowac sie w implementacji JavaFX, musi to byc observableList
    //specjalna lista ktora implementuje wzorzec obserwator, ponadto okreslony musi byc model danych (EmployeeTableMOdel)

    private final ObservableList<EmployeeTableModel> data;

    @FXML
    private TableView<EmployeeTableModel> employeeTable;


    @FXML
    private Button addButton;
    @FXML
    private Button refreshButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button viewButton;

    public EmployeeController() {
        data= FXCollections.observableArrayList();
        employeeRestClient =new EmployeeRestClient();
        popupFactory = new PopupFactory();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAddButton();
        initializeRefreshButton();
        initializeTableView();
        initializeViewEmployeeButton();
        initializeEditButton();
        initializeDeleteEmployeeButton();

    }

    private void initializeDeleteEmployeeButton() {
        deleteButton.setOnAction(x->{
            EmployeeTableModel selectedEmployee=employeeTable.getSelectionModel().getSelectedItem();
            if(selectedEmployee!=null){
                try {
                    Stage deleteEmployeeStage = createEditEmployeeStage(); //taki sam Stage co EditEmployee
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(deleteEmployee_FXML));
                    Scene scene = new Scene(loader.load(),600,500);
                    deleteEmployeeStage.setScene(scene);
                    DeleteEmployeeController controller = loader.getController();
                    controller.loadEmployeeData(selectedEmployee);
                    deleteEmployeeStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initializeEditButton() {
        editButton.setOnAction(x->{
            EmployeeTableModel selectedEmployee=employeeTable.getSelectionModel().getSelectedItem();
            if(selectedEmployee!=null){
                try {
                    Stage waintingPopup = popupFactory.createWaintingPopup("Loading employee data...");
                    waintingPopup.show();
                    Stage editEmployeeStage=createEditEmployeeStage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(editEmployee_FXML));
                    Scene scene = new Scene(loader.load(),600,500);
                    editEmployeeStage.setScene(scene);
                    EditEmployeeController controller = loader.getController();
                    controller.loadEmployeeData(selectedEmployee.getIdEmployee(),()->{
                        waintingPopup.close();
                        editEmployeeStage.show();
                    });
                } catch (IOException e) {
                    throw new RuntimeException("Can't load fxml file: "+editEmployee_FXML);
                }
            }
        });
    }

    private Stage createEditEmployeeStage() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    private void initializeViewEmployeeButton() {
        viewButton.setOnAction(x->{
            EmployeeTableModel employee = employeeTable.getSelectionModel().getSelectedItem(); //pobieramy zaznaczonego pracownika
            if(employee==null){
                return;
            }else{

                try {
                    Stage waintingPopup = popupFactory.createWaintingPopup("Loading employee data....");
                    waintingPopup.show();
                    Stage viewEmployeeStage = new Stage();
                    viewEmployeeStage.initStyle(StageStyle.UNDECORATED);
                    viewEmployeeStage.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(viewEmployee_FXML));
                    Scene scene =new Scene((BorderPane) loader.load(),600,500 );
                    viewEmployeeStage.setScene(scene);
                    ViewEmployeeController controller = loader.<ViewEmployeeController>getController();
                    controller.loadEmployeeData(employee.getIdEmployee(),()->{
                        waintingPopup.close();
                        viewEmployeeStage.show();
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        });
    }

    private void initializeRefreshButton() {
        refreshButton.setOnAction(x->{
            loadEmployeeData();
        });
    }

    private void initializeAddButton() {
        addButton.setOnAction((x)->{
            Stage addEmployeeStage=new Stage();
            addEmployeeStage.initStyle(StageStyle.UNDECORATED);//usuwanie gornego paska w oknie
            addEmployeeStage.initModality(Modality.APPLICATION_MODAL);//fokus na nowym oknie
            try {
                Parent addEmployeeParent = FXMLLoader.load(getClass().getResource(addEmployee_FXML));
                Scene scene = new Scene(addEmployeeParent,600,400);
                addEmployeeStage.setScene(scene);
                addEmployeeStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void initializeTableView() {
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);//dzieki temu dodawanie kolejnych kolumn sprawi ze beda zajmowaly rowna szerokosc
        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setMinWidth(100);
        //firstNameColumn.setCellValueFactory(new PropertyValueFactory<nadrzedny model,typ wartosci>("nazwaPola")); okresla jakie wartosci bedzie przechowywala poszczegolna kolumna
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel,String>("firstName"));

        TableColumn lastNameColumn = new TableColumn("Last Name");
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel,String>("lastName"));

        TableColumn salaryColumn = new TableColumn("Salary");
        salaryColumn.setMinWidth(100);
        salaryColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel,String>("salary"));

        employeeTable.getColumns().addAll(firstNameColumn,lastNameColumn,salaryColumn); //przypisanie kolumn do glownej tabeli
        //w przypadku Tabel JavaFx dane ktore przekazujemy do tabeli musza znajdowac sie w implementacji JavaFX, musi to byc observableList
        //specjalna lista ktora implementuje wzorzec obserwator, ponadto okreslony musi byc model danych (EmployeeTableMOdel)


        loadEmployeeData();//przekazujemy liste ktora implementuje interfejs observable

        employeeTable.setItems(data);
    }

    private void loadEmployeeData() {
        //tworzymy nowy watek, aby w glownym watku NIe byly wykonywane zapytania do serwera zewnetrznego, to zawiesza aplikacje
        Thread thread = new Thread(()->{
            //pobiramy pracownikow
            List<EmployeeDto> employees = employeeRestClient.getEmployees();
            data.clear();
            data.addAll(employees.stream().map(EmployeeTableModel::of).collect(Collectors.toList()));//przerobienie listy EmployeeDto na EmployeeTableModel
        });
        thread.start();
    }

}