package com.example.SystemBackend.dto;

import com.example.SystemBackend.entity.Warehouse;
import lombok.Data;

//ta klasa posluzy do przekazywania miedzy Frontend a Backend dane magazynu
@Data
public class WarehouseDto {
    private Long idWarehouse;
    private String name;

    public static WarehouseDto of(Warehouse warehouse){
        WarehouseDto dto = new WarehouseDto();
        dto.setIdWarehouse(warehouse.getIdWarehouse());
        dto.setName(warehouse.getName());
        return dto;
    }
}
