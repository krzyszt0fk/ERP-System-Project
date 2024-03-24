package com.example.SystemBackend.controller;


import com.example.SystemBackend.entity.Item;
import com.example.SystemBackend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;

    //end point do zapisywania itemu
    @PostMapping("/items")
    public Item newItem(@RequestBody Item newItem){
        return itemRepository.save(newItem);

    }
    // endpoint ktory bedzie zwracal liste wszystkich itemow
    @GetMapping("/items")
    public List<Item> listItems(){
        return itemRepository.findAll();
    }

    //usuwanie itemu - zwracanie obiektu typu ResponseEntity, informuje czy usuwanie wykona sie poprawnie
    @DeleteMapping(("/items"))
    public ResponseEntity deleteItem(@RequestBody Long idItem){
        itemRepository.deleteById(idItem);
        return ResponseEntity.ok().build();
    }



}
