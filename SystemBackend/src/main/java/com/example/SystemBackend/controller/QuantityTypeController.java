package com.example.SystemBackend.controller;

import com.example.SystemBackend.dto.QuantityTypeDto;
import com.example.SystemBackend.entity.QuantityType;
import com.example.SystemBackend.repository.QuantityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class QuantityTypeController {
    private final QuantityTypeRepository quantityTypeRepository;


    //end point do zapisywania quantity
    @PostMapping("/quantity")
    public QuantityType newQuantityType(@RequestBody QuantityType quantityType){
        return quantityTypeRepository.save(quantityType);

    }
    // endpoint ktory bedzie zwracal liste wszystkich quantity
    @GetMapping("/quantity")
    public List<QuantityTypeDto> listQuantity(){

        return quantityTypeRepository.findAll().stream().map(QuantityTypeDto::of).collect(Collectors.toList());
    }

    //usuwanie quantity - zwracanie obiektu typu ResponseEntity, informuje czy usuwanie wykona sie poprawnie
    @DeleteMapping(("/quantity"))
    public ResponseEntity deleteQuantity(@RequestBody Long idQuantityType){
        quantityTypeRepository.deleteById(idQuantityType);
        return ResponseEntity.ok().build();
    }
}
