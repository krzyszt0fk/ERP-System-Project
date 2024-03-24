package com.example.SystemBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperator;
    @Column
    private String login;
    @Column
    private String password;
@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL) //FetchType - w momencie pobrania obiektu operator zostanie zaciagniety obiekt Employee
@JoinColumn(name= "idEmployee")
    private Employee employee;                               //CascadeType - jesli usuniemy operatpra to od razu usunie sie employee

}
