package com.example.SystemBackend.controller;


import com.example.SystemBackend.dto.EmployeeDto;
import com.example.SystemBackend.dto.ItemDto;
import com.example.SystemBackend.dto.ItemEditViewDto;
import com.example.SystemBackend.dto.ItemSaveDto;
import com.example.SystemBackend.entity.Employee;
import com.example.SystemBackend.entity.Item;
import com.example.SystemBackend.repository.ItemRepository;
import com.example.SystemBackend.repository.QuantityTypeRepository;
import com.example.SystemBackend.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final QuantityTypeRepository quantityTypeRepository;


    //end point do zapisywania itemu
    @PostMapping("/items")
    public ItemDto newItem(@RequestBody ItemSaveDto dto){
        if (dto.getIdItem()==null){
        return ItemDto.of(itemService.saveItem(dto));
        }else{
            Item item = itemRepository.findById(dto.getIdItem()).get();
            item.setName(dto.getName());
            item.setQuantity(dto.getQuantity());
            item.setQuantityType(quantityTypeRepository.findById(dto.getIdQuantityType()).get());
            return ItemDto.of(itemRepository.save(item));
        }

    }
    // endpoint ktory bedzie zwracal liste wszystkich itemow
    @GetMapping("/items")
    public List<ItemDto> listItems(){

        return itemRepository.findAll()
                .stream()
                .map(ItemDto::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/items/{idItem}")
    public ItemDto getItem(@PathVariable Long idItem ){

        Optional<Item> optionalItem=itemRepository.findById(idItem);
        return ItemDto.of(optionalItem.get());

    }
    @GetMapping("/item_edit/{idItem}")
    public ItemEditViewDto getItemEditDto(@PathVariable Long idItem ){

        Item item=itemRepository.findById(idItem).get();
        ItemEditViewDto dto =  ItemEditViewDto.of(item, quantityTypeRepository.findAll());
        return dto;

    }
    //usuwanie itemu - zwracanie obiektu typu ResponseEntity, informuje czy usuwanie wykona sie poprawnie
    @DeleteMapping(("/items/{idItem}"))
    public ResponseEntity deleteItem(@PathVariable Long idItem){
        itemRepository.deleteById(idItem);
        return ResponseEntity.ok().build();
    }



}
