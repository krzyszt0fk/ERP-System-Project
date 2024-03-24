package com.example.SystemBackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWarehouse;
    @Column
    private String name;
@ManyToMany(mappedBy = "warehouses")
    private Set<Item> items;
}
