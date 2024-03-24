package com.example.demo.rest;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.handler.DeletedEmployeeHandler;
import com.example.demo.handler.SavedEmployeeHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

//klasa ktora bedzie sie komunikowala z serwerem Backend i bedzie pobierala wszystkich pracownikow
public class EmployeeRestClient {
    private static final String EMPLOYEES_URL = "http://localhost:8080/employees";//sciezka do endpointa ktory wskazuje liste pracownikow
    private final RestTemplate restTemplate; //do zadan HTTP

    public EmployeeRestClient(){
        restTemplate=new RestTemplate();

    }
    public List<EmployeeDto> getEmployees(){//metoda zwracajaca liste employeeDto

        ResponseEntity<EmployeeDto[]> employeesResponse = restTemplate.getForEntity(EMPLOYEES_URL, EmployeeDto[].class);
        return Arrays.asList(employeesResponse.getBody());//przerabia tabele na liste, zwracamy liste
    }

    public void saveEmployee(EmployeeDto dto, SavedEmployeeHandler handler) {//po zapisaniu odpowiednie okno ma sie zamknac(stad handler)
        ResponseEntity<EmployeeDto> responseEntity = restTemplate.postForEntity(EMPLOYEES_URL, dto, EmployeeDto.class);
        //sprawdzamy czy status jest ok(200)
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            handler.handle();
        }else{
            //TODO implement
        }
    }

    public EmployeeDto getEmployee(Long idEmployee) {
        String url=EMPLOYEES_URL+"/"+idEmployee;
        ResponseEntity<EmployeeDto> responseEntity = restTemplate.getForEntity(url, EmployeeDto.class);
        //TODO implement
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            return responseEntity.getBody();
        }else throw new RuntimeException("Can't load employee");
    }

    public void deleteEmployee(Long idEmployee, DeletedEmployeeHandler handler) {
        restTemplate.delete(EMPLOYEES_URL+"/"+idEmployee);
        handler.handle();
    }
}
