package com.example.demo.rest;

import com.example.demo.dto.ItemDto;
import com.example.demo.dto.ItemEditViewDto;
import com.example.demo.dto.ItemSaveDto;
import com.example.demo.handler.ProcessFinishedHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ItemRestClient {
    private static final String ITEMS_URL = "http://localhost:8080/items";
    private static final String ITEM_EDIT_URL = "http://localhost:8080/item_edit";
    private final RestTemplate restTemplate;

    public ItemRestClient() {
        restTemplate = new RestTemplate();
    }

    //metoda pobierajaca liste przedmiotow
    public List<ItemDto> getItems(){//url i format w jakim jest odpowiedz (tabela item dto)
        ResponseEntity<ItemDto[]> itemResponseEntity = restTemplate.getForEntity(ITEMS_URL, ItemDto[].class);
        return Arrays.asList(itemResponseEntity.getBody());

    }

    public void saveItem(ItemSaveDto dto, ProcessFinishedHandler handler) {
        ResponseEntity<ItemDto> responseEntity = restTemplate.postForEntity(ITEMS_URL, dto, ItemDto.class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            handler.handle();
        }else{
            throw new RuntimeException("Can't save dto: " +dto);
        }
    }

    public ItemDto getItem(Long idItem) {
        ResponseEntity<ItemDto> responseEntity = restTemplate.getForEntity(ITEMS_URL + "/" + idItem, ItemDto.class);
        return responseEntity.getBody();
    }

    public ItemEditViewDto getEditItemData(Long idItem) {
        ResponseEntity<ItemEditViewDto> responseEntity = restTemplate.getForEntity(ITEM_EDIT_URL + "/" + idItem, ItemEditViewDto.class);
        return responseEntity.getBody();
    }

    public void deleteItem(Long idItemToDelete) {
        restTemplate.delete(ITEMS_URL+"/"+idItemToDelete);
    }
}
