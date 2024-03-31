package com.example.SystemBackend.service;

import com.example.SystemBackend.dto.ItemSaveDto;
import com.example.SystemBackend.dto.WarehouseDto;
import com.example.SystemBackend.entity.Item;
import com.example.SystemBackend.entity.QuantityType;
import com.example.SystemBackend.entity.Warehouse;
import com.example.SystemBackend.repository.ItemRepository;
import com.example.SystemBackend.repository.QuantityTypeRepository;
import com.example.SystemBackend.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final WarehouseRepository warehouseRepository;
    private final QuantityTypeRepository quantityTypeRepository;

    public Item saveItem(ItemSaveDto dto){
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(dto.getIdWarehouse());
        Optional<QuantityType> quantityTypeOptional = quantityTypeRepository.findById(dto.getIdQuantityType());
        if(!warehouseOptional.isPresent() || !quantityTypeOptional.isPresent()){
            throw new RuntimeException("Incorrect identifiers: idWarehouse: "+dto.getIdWarehouse()+" , idQuantityType: " +dto.getIdQuantityType());
        }
        Warehouse warehouse = warehouseOptional.get();
        QuantityType quantityType = quantityTypeOptional.get();

        Item item=Item.of(dto);
        item.setQuantityType(quantityType);
        item.setWarehouse(warehouse);
        return itemRepository.save(item);

    }
}
