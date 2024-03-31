package com.example.SystemBackend.dto;

import com.example.SystemBackend.entity.QuantityType;
import lombok.Data;

@Data
public class QuantityTypeDto {

    private Long idQuantutyType;
    private String name;

  public static QuantityTypeDto of(QuantityType quantityType){
      QuantityTypeDto dto = new QuantityTypeDto();
      dto.setName(quantityType.getName());
      dto.setIdQuantutyType(quantityType.getIdQuantityType());
      return dto;
  }
}
