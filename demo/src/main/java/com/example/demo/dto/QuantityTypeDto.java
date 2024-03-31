package com.example.demo.dto;

import lombok.Data;

@Data
public class QuantityTypeDto {
    private Long idQuantutyType;
    private String name;

@Override
    public String toString(){
    return name;
}
}
