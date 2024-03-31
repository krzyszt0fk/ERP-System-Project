package com.example.demo.rest;

import com.example.demo.dto.WarehouseModuleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WarehouseRestClient {
    private static final String WAREHOUSE_MODULE_URL = "http://localhost:8080/warehouse_module_data";
    private final RestTemplate restTemplate;

    public WarehouseRestClient() {
        restTemplate = new RestTemplate();
    }

    public WarehouseModuleDto getWarehouseModuleData(){//nie przyjmuje parametru, zwraca pierwszy magazyn,
        ResponseEntity<WarehouseModuleDto> responseEntity = restTemplate.getForEntity(WAREHOUSE_MODULE_URL, WarehouseModuleDto.class);
        return responseEntity.getBody();
    }

    public WarehouseModuleDto getWarehouseModuleData(Long idWarehouse){
        ResponseEntity<WarehouseModuleDto> responseEntity = restTemplate.getForEntity(WAREHOUSE_MODULE_URL+ "?idWarehouse="+idWarehouse, WarehouseModuleDto.class);
        return responseEntity.getBody();
    }
}
