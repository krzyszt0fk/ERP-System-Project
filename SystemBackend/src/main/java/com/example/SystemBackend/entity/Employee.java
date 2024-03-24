package com.example.SystemBackend.entity;

import com.example.SystemBackend.dto.EmployeeDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data //dzieki lombok settery i gettery zostana utworzone automatycznie
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String salary;
    @OneToOne(mappedBy = "employee")
    private Operator operator;
//metoda statyczna tworzaca encje Employee w oparciu o EmployeeDTO
    public static Employee of(EmployeeDto dto){
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setSalary(dto.getSalary());
        return employee;
    }

    public void updateEmployee(EmployeeDto dto) {
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setSalary(dto.getSalary());
    }
}
