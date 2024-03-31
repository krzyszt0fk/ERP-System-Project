package com.example.SystemBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//przekazywanie kompletnego widoku magazynowego
@Data
@NoArgsConstructor //utowrzy konstruktor bezparametrowy
@AllArgsConstructor //utworzy kontruktor przyjmujacy 3 ponizsze pola
public class WarehouseModuleDto {
    private List<ItemDto> itemDtoList;
    private List<WarehouseDto> warehouseDtoList;
    private WarehouseDto selectedWarehouse;
}
