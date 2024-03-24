package com.example.demo.dto;//klasa ktora odbieramy od backend info

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpeAuthResultDto {
    private Long idOperator;
    private String firstName;
    private String lastName;
    private boolean authenticated;

}
