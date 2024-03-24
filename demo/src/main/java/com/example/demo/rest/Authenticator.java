package com.example.demo.rest;

import com.example.demo.dto.OperatorCredentialsDto;
import com.example.demo.handler.AuthenticationResultHandler;

public interface Authenticator {
    void authenticate(OperatorCredentialsDto operatorCredentialsDto, AuthenticationResultHandler authenticationResultHandler);
}
