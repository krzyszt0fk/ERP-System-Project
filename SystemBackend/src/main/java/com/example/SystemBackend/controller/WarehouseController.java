package com.example.SystemBackend.controller;

import com.example.SystemBackend.entity.Warehouse;
import com.example.SystemBackend.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;

    //end point do zapisywania warehouse
    @PostMapping("/warehouse")
    public Warehouse newWarehouse(@RequestBody Warehouse warehouse){
        return warehouseRepository.save(warehouse);

    }
    // endpoint ktory bedzie zwracal liste wszystkich warehouses
    @GetMapping("/warehouse")
    public List<Warehouse> listWarehouse(){
        return warehouseRepository.findAll();
    }

    //usuwanie warehouse - zwracanie obiektu typu ResponseEntity, informuje czy usuwanie wykona sie poprawnie
    @DeleteMapping(("/warehouse"))
    public ResponseEntity deleteWarehouse(@RequestBody Long idWarehouse){
        warehouseRepository.deleteById(idWarehouse);
        return ResponseEntity.ok().build();
    }
}
