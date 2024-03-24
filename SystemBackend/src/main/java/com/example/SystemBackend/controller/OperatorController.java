package com.example.SystemBackend.controller;


import com.example.SystemBackend.dto.OpeAuthResultDto;
import com.example.SystemBackend.dto.OperatorCredentialsDto;
import com.example.SystemBackend.entity.Operator;
import com.example.SystemBackend.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OperatorController {
    private final OperatorRepository operatorRepository;

    //end point do zapisywania operatora
    @PostMapping("/operator")
    public Operator newOperator(@RequestBody Operator operator){
        return operatorRepository.save(operator);

    }
    // endpoint ktory bedzie zwracal liste wszystkich operatorow
    @GetMapping("/operator")
    public List<Operator> listOperators(){
        return operatorRepository.findAll();
    }

    //usuwanie operatora - zwracanie obiektu typu ResponseEntity, informuje czy usuwanie wykona sie poprawnie
    @DeleteMapping(("/operator"))
    public ResponseEntity deleteOperator(@RequestBody Long idOperator){
        operatorRepository.deleteById(idOperator);
        return ResponseEntity.ok().build();
    }
    //endpoint ktory bedzie przyjmował OperatorCredentialsDto i zwracał OpeAuthResultDto(jesli haslo sie zgadza)
    @PostMapping("/verify_operator_credentials")
    public OpeAuthResultDto verifyOperatorCredentials(@RequestBody OperatorCredentialsDto operatorCredentialsDto){
        Optional<Operator> operatorOptional = operatorRepository.findByLogin(operatorCredentialsDto.getLogin());
        if(!operatorOptional.isPresent()){ //jesli nie ma operatora zwracamy obiekt ktory mowi ze uwierzytelnianie sie nie powiodło
            return OpeAuthResultDto.createUnauthenticated();
        }
        Operator operator = operatorOptional.get();
        if(!operator.getPassword().equals(operatorCredentialsDto.getPassword())){//jezeli jest taki operator ale haslo sie nie zgadza to tez zwaracamy taki obiekt
            return OpeAuthResultDto.createUnauthenticated();
        }else { //jeżeli jest operator i hasło sie zgadza
            return OpeAuthResultDto.of(operator);
        }

    }
}
