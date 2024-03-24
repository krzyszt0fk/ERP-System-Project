package com.example.demo.handler;

import com.example.demo.dto.OpeAuthResultDto;
//obiekt tego interfejsu odbieramy w LoginController
@FunctionalInterface
public interface AuthenticationResultHandler {
    void handle(OpeAuthResultDto opeAuthResultDto);
}
