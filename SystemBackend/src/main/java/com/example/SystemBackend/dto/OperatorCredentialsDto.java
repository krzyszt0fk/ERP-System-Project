package com.example.SystemBackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperatorCredentialsDto {
    private String login;
    private String password;

}
