package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class ItemSaveDto {
    private String name;
    private Double quantity;
    private Long idQuantityType;
    private Long idWarehouse;
}
