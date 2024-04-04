package com.example.demo.rest;

import com.example.demo.dto.OpeAuthResultDto;
import com.example.demo.dto.OperatorCredentialsDto;
import com.example.demo.handler.AuthenticationResultHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class AuthenticatorImpl implements Authenticator {
    private final RestTemplate restTemplate;
    private static final String Authentication_URL="http://localhost:8080/verify_operator_credentials";
    public AuthenticatorImpl(){
        restTemplate = new RestTemplate();
    }
    @Override
    public void authenticate(OperatorCredentialsDto operatorCredentialsDto, AuthenticationResultHandler authenticationResultHandler) {
        Runnable authenticationTask = () -> {
            processAuthentication(operatorCredentialsDto, authenticationResultHandler);
        };
        Thread authenticationThread = new Thread(authenticationTask);
        authenticationThread.setDaemon(true);
        authenticationThread.start();

    }

    private void processAuthentication(OperatorCredentialsDto operatorCredentialsDto, AuthenticationResultHandler authenticationResultHandler) {//przeprowadzany proces uwierzytelniania

        ResponseEntity<OpeAuthResultDto> responseEntity = restTemplate.postForEntity(Authentication_URL, operatorCredentialsDto, OpeAuthResultDto.class);//url ktory jest na backend i bedzie tam uwierzytelniany
//        OpeAuthResultDto dto = new OpeAuthResultDto();
//        dto.setAuthenticated(true);
//        dto.setFirstName("Krzysztof");
//        dto.setLastName("Ksiezki");
//        dto.setIdOperator(1L);
        authenticationResultHandler.handle(responseEntity.getBody());
    }
}
