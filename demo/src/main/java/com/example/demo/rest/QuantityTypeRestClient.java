package com.example.demo.rest;

import com.example.demo.dto.QuantityTypeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class QuantityTypeRestClient {
    private static final String Quantity_Types_URL =  "http://localhost:8080/quantity";
    private final RestTemplate restTemplate;

    public QuantityTypeRestClient() {
        restTemplate = new RestTemplate();
    }
    public List<QuantityTypeDto> getQuantityTypes(){
        ResponseEntity<QuantityTypeDto[]> quantityTypeResponseEntity = restTemplate.getForEntity(Quantity_Types_URL, QuantityTypeDto[].class);
        return Arrays.asList(quantityTypeResponseEntity.getBody());
    }
}
