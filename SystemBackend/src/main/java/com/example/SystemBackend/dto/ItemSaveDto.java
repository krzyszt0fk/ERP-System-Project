package com.example.SystemBackend.dto;

import lombok.Data;

@Data
public class ItemSaveDto {
    private Long idItem;
    private String name;
    private Double quantity;
    private Long idQuantityType;
    private Long idWarehouse;

}
