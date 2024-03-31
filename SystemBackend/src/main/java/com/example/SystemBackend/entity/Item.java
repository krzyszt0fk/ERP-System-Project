package com.example.SystemBackend.entity;

import com.example.SystemBackend.dto.ItemSaveDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;
    @Column
    private String name;

    @Column
    private Double quantity;
    @ManyToOne(fetch = FetchType.EAGER) //chcemy za kazdym razem pobierac jaki to rodzaj
    @JoinColumn(name="idQuantityType")
    private QuantityType quantityType;
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name="idWarehouse")
    private Warehouse warehouse;

    public static Item of(ItemSaveDto dto) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setQuantity(dto.getQuantity());
        return item;
    }
}
