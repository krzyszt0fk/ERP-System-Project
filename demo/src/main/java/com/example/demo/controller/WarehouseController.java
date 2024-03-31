package com.example.demo.controller;

import com.example.demo.dto.WarehouseDto;
import com.example.demo.dto.WarehouseModuleDto;
import com.example.demo.rest.ItemRestClient;
import com.example.demo.rest.WarehouseRestClient;
import com.example.demo.table.ItemTableModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WarehouseController implements Initializable {
    private static final String ADD_ITEM_FXML = "/com/example/demo/addItem.fxml" ;
    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button viewButton;

    @FXML
    private BorderPane warehouseBP;

    @FXML
    private TableView<ItemTableModel> warehouseTV;
    @FXML
    private ComboBox<WarehouseDto> warehouseCB;

    private final ItemRestClient itemRestClient;

    private final WarehouseRestClient warehouseRestClient;
    private ObservableList<ItemTableModel> data;

    public WarehouseController() {
        itemRestClient = new ItemRestClient();
        data = FXCollections.observableArrayList();
        warehouseRestClient = new WarehouseRestClient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableView();
        initializeComboBox();
        initializeAddItemButton();
    }

    private void initializeAddItemButton() {
        addButton.setOnAction(x->{
            try {
                Stage stage = createItemCrudStage();
                FXMLLoader loader= new FXMLLoader(getClass().getResource(ADD_ITEM_FXML));
                Scene scene = new Scene(loader.load(),600,400);
                stage.setScene(scene);
                AddItemController controller = loader.getController();
                WarehouseDto selectedWarehouseDto = warehouseCB.getSelectionModel().getSelectedItem();
                controller.setWarehouseDto(selectedWarehouseDto);
                controller.loadQuantityTypes();
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Stage createItemCrudStage() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    private void initializeComboBox() {
        warehouseCB.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            //oldValue reprezentuje ostatni zaznaczony element , newValue - nowy wybrany element
            if(newValue==null){
                return;
            }
            if(!newValue.equals(oldValue)&&oldValue!=null){
                WarehouseDto warehouseDto = warehouseCB.getSelectionModel().getSelectedItem();
                loadItemData(warehouseDto);
            }

        }));

    }

    private void initializeTableView() {
        warehouseTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel,String>("name"));

        TableColumn quantityColumn = new TableColumn("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel,Double>("quantity"));

        TableColumn quantityTypeColumn = new TableColumn("Quantity Type");
        quantityTypeColumn.setMinWidth(100);
        quantityTypeColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel,String>("quantityType"));

        warehouseTV.getColumns().addAll(nameColumn, quantityColumn,quantityTypeColumn);
        warehouseTV.setItems(data);
        loadItemData();

    }

    private void loadItemData(){
        Thread thread = new Thread(()->{
            WarehouseModuleDto warehouseModuleDto = warehouseRestClient.getWarehouseModuleData();
            data.clear();
            setWarehouseComboBoxItem(warehouseModuleDto);
            data.addAll(warehouseModuleDto.getItemDtoList().stream().map(ItemTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }
    private void loadItemData(WarehouseDto warehouseDto){
        Thread thread = new Thread(()->{
            WarehouseModuleDto warehouseModuleDto = warehouseRestClient.getWarehouseModuleData(warehouseDto.getIdWarehouse());
            data.clear();
            setWarehouseComboBoxItem(warehouseModuleDto);
            data.addAll(warehouseModuleDto.getItemDtoList().stream().map(ItemTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }

    private void setWarehouseComboBoxItem(WarehouseModuleDto warehouseModuleDto) {//wypelnienie danych dot. magazynow w ComboBOx
        List<WarehouseDto> warehouseDtoList = warehouseModuleDto.getWarehouseDtoList();
        ObservableList<WarehouseDto> warehouseComboBoxItems = FXCollections.observableArrayList();
        warehouseComboBoxItems.addAll(warehouseDtoList);
        //przypisanie magazynow do warehouseComboBox
        Platform.runLater(() -> {
            //wywola sie w glownym watku
            warehouseCB.setItems(warehouseComboBoxItems);
            warehouseCB.getSelectionModel().select(warehouseDtoList.indexOf(warehouseModuleDto.getSelectedWarehouse()));
        });

    }}
