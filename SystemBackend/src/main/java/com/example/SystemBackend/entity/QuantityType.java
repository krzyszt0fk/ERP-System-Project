package com.example.SystemBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class QuantityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuantityType;
    @Column
    private String name;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "quantityType")
    private List<Item> items;

}
