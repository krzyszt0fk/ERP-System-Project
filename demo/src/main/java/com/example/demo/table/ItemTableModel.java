package com.example.demo.table;

import com.example.demo.dto.ItemDto;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemTableModel {
    private final Long idItem;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty quantity;
    private final SimpleStringProperty quantityType;

    public ItemTableModel(Long idItem, String name, Double quantity, String quantityType){
        this.idItem=idItem;
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.quantityType = new SimpleStringProperty(quantityType);
    }

    public static ItemTableModel of (ItemDto dto){
      return  new ItemTableModel(dto.getIdItem(), dto.getName(), dto.getQuantity(), dto.getQuantityType());
    }

    public Long getIdItem() {
        return idItem;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public Double getQuantity() {
        return quantity.get();
    }

    public SimpleDoubleProperty quantityProperty() {
        return quantity;
    }

    public String getQuantityType() {
        return quantityType.get();
    }

    public SimpleStringProperty quantityTypeProperty() {
        return quantityType;
    }
}
