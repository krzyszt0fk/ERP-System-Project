package com.example.SystemBackend.entity;

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
@ManyToMany
@JoinTable(name = "item_warehouse" , joinColumns =@JoinColumn(name = "idItem"), inverseJoinColumns = @JoinColumn(name = "idWarehouse"))
    private Set<Warehouse> warehouses;

}
