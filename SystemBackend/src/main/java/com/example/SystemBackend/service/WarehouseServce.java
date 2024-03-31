//Service jako warstwa posredniczaca pomiedzy repozytoriami a controllerami
package com.example.SystemBackend.service;

import com.example.SystemBackend.dto.ItemDto;
import com.example.SystemBackend.dto.WarehouseDto;
import com.example.SystemBackend.dto.WarehouseModuleDto;
import com.example.SystemBackend.entity.Warehouse;
import com.example.SystemBackend.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WarehouseServce {
    private final WarehouseRepository warehouseRepository;

//zwraca warehousemoduleData gdy nie ma zaznaczonego po stronie frontend zadnego magazynu , wtedy jako selected wybierze pierwszy z pobranych
    public WarehouseModuleDto getWarehouseModuleData(){
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        List<WarehouseDto> warehouseDtoList = warehouseList.stream().map(WarehouseDto::of).collect(Collectors.toList());
        List<ItemDto> itemDtoList = warehouseList.get(0).getItems().stream().map(ItemDto::of).collect(Collectors.toList());
        WarehouseDto selectedWarehouseDto = WarehouseDto.of(warehouseList.get(0)); // utworzenie zaznaczonego magazynu DTO
        WarehouseModuleDto warehouseModuleDto = new WarehouseModuleDto(itemDtoList,warehouseDtoList, selectedWarehouseDto);
        return warehouseModuleDto;
    }
//kiedy uzytkownik zaznaczy magazyn do wyswietlania jego przedmiotow
    public WarehouseModuleDto getWarehouseModuleData(Long idWarehouse){
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        List<WarehouseDto> warehouseDtoList = warehouseList.stream().map(WarehouseDto::of).collect(Collectors.toList());

        Optional<Warehouse> optionalWarehouse = warehouseList.stream().filter(x -> idWarehouse.equals(x.getIdWarehouse())).findFirst();
        Warehouse selectedWarehouse = optionalWarehouse.get();

        List<ItemDto> itemDtoList = selectedWarehouse.getItems().stream().map(ItemDto::of).collect(Collectors.toList());

        WarehouseModuleDto warehouseModuleDto = new WarehouseModuleDto(itemDtoList,warehouseDtoList, WarehouseDto.of(selectedWarehouse));
        return warehouseModuleDto;
    }
}
