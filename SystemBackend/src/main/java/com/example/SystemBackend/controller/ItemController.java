package com.example.SystemBackend.controller;


import com.example.SystemBackend.dto.ItemDto;
import com.example.SystemBackend.dto.ItemSaveDto;
import com.example.SystemBackend.entity.Item;
import com.example.SystemBackend.repository.ItemRepository;
import com.example.SystemBackend.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    //end point do zapisywania itemu
    @PostMapping("/items")
    public ItemDto newItem(@RequestBody ItemSaveDto dto){
        return ItemDto.of(itemService.saveItem(dto));

    }
    // endpoint ktory bedzie zwracal liste wszystkich itemow
    @GetMapping("/items")
    public List<ItemDto> listItems(){

        return itemRepository.findAll()
                .stream()
                .map(ItemDto::of)
                .collect(Collectors.toList());
    }

    //usuwanie itemu - zwracanie obiektu typu ResponseEntity, informuje czy usuwanie wykona sie poprawnie
    @DeleteMapping(("/items"))
    public ResponseEntity deleteItem(@RequestBody Long idItem){
        itemRepository.deleteById(idItem);
        return ResponseEntity.ok().build();
    }



}
