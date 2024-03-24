package com.example.demo.table;

import com.example.demo.dto.EmployeeDto;
import javafx.beans.property.SimpleStringProperty;

// model danych dla tabeli zawierajacej pracownikow
public class EmployeeTableModel {
    private final Long idEmployee;
    private final SimpleStringProperty firstName; //dzieki SimpleStringProperty tabele JavaFX beda mogly obserwowac zmiany nie tylko w obiekcie ale tez w jego polach
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty salary;

    public EmployeeTableModel(Long idEmployee, String firstName, String lastName, String salary){//dane w postaci String pobierane beda z Backendu

        this.firstName=new SimpleStringProperty(firstName);
        this.lastName=new SimpleStringProperty(lastName);
        this.salary=new SimpleStringProperty(salary);
        this.idEmployee = idEmployee;
    }
    //statyczne metoda ktora przerabia EmployeeDto i zwracala obiekt EmployeeTableModel
    public static EmployeeTableModel of (EmployeeDto dto){
        return new EmployeeTableModel(dto.getIdEmployee(),dto.getFirstName(), dto.getLastName(), dto.getSalary());
    }
//nie pobieram SimpleStringProperty tylko getterem od razu String, getFirstName na obiekcie firstName wywoluje metode get(), ktora zwraca wartosc String
    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getSalary() {
        return salary.get();
    }

    public SimpleStringProperty salaryProperty() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary.set(salary);
    }

    public Long getIdEmployee() {
        return idEmployee;
    }
}
