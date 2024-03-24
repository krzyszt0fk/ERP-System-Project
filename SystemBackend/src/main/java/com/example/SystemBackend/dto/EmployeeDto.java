package com.example.SystemBackend.dto;

import com.example.SystemBackend.entity.Employee;
import lombok.Data;
// dto dla danych dotyczacych pracownikow
@Data
public class EmployeeDto {
    private Long idEmployee;
    private String firstName;
    private String lastName;
    private String salary;

    public static EmployeeDto of(Employee employee){ //metoda statyczna ktora mapuje encje Employee na EmployeeDTO

        EmployeeDto dto = new EmployeeDto();
        dto.setIdEmployee(employee.getIdEmployee());//przypisanie danych do pol
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setSalary(employee.getSalary());
        return dto;
    }

}
