package com.example.SystemBackend.controller;
import com.example.SystemBackend.dto.EmployeeDto;
import com.example.SystemBackend.entity.Employee;
import com.example.SystemBackend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    //end point do zapisywania pracownika
    @PostMapping("/employees")
    EmployeeDto saveOrUpdateEmployee(@RequestBody EmployeeDto dto){
        if(dto.getIdEmployee() == null){
            return EmployeeDto.of(employeeRepository.save(Employee.of(dto)));
        }else{
            Optional<Employee> optionalEmployee = employeeRepository.findById(dto.getIdEmployee());
            if(optionalEmployee.isPresent()){
                Employee employee = optionalEmployee.get();
                employee.updateEmployee(dto);
                return EmployeeDto.of(employeeRepository.save(employee));
            }else {
                throw new RuntimeException("Can't find user with given id: "+dto.getIdEmployee());
            }
        }


    }
    // endpoint ktory bedzie zwracal liste wszystkich pracownikow
    @GetMapping("/employees")
    public List<EmployeeDto> listEmployees(){

        return employeeRepository.findAll()
                .stream()//za pomoca stream zamieniamy liste na strumien
                .map(EmployeeDto::of)// na strumieniu mozna wywolac metode map, to co w tej metodzie przekazemy bedzie wywolane na kazdym obiekcie listy
                //konstruktor EmployeeDTO bedzie wywolywany na kazdym obiekcie
                .collect(Collectors.toList());//wynik (EmployeeDto) zbiramy przy pomocy metody collect
    }

    //endpoint odpowiedzialny za wczytywanie pojedyńczego pracownika
    @GetMapping("/employees/{idEmployee}")
        public EmployeeDto getEmployee(@PathVariable Long idEmployee ){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Optional<Employee> optionalEmployee=employeeRepository.findById(idEmployee);
            return EmployeeDto.of(optionalEmployee.get());

    }


    //usuwanie pracownika - zwracanie obiektu typu ResponseEntity, informuje czy usuwanie wykona sie poprawnie
    @DeleteMapping(("/employees/{idEmployee}"))
    public ResponseEntity deleteEmployee(@PathVariable Long idEmployee){
        employeeRepository.deleteById(idEmployee);
        return ResponseEntity.ok().build();
    }
    //endpoint odpowiedzialny za wczytywanie pojedyńczego pracownika


}
