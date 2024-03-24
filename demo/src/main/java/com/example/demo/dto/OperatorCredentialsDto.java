package com.example.demo.dto; //ta klasa do wysylania info
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperatorCredentialsDto {
    private String login;
    private String password;

}
