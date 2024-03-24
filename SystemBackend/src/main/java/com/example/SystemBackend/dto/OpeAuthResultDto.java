package com.example.SystemBackend.dto;

import com.example.SystemBackend.entity.Operator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpeAuthResultDto {
    private Long idOperator;
    private String firstName;
    private String lastName;
    private boolean authenticated;

    public static OpeAuthResultDto createUnauthenticated(){
        OpeAuthResultDto dto = new OpeAuthResultDto();
        dto.setAuthenticated(false);
        return dto;
    }
//metoda statyczna ktora przepisuje dane z encji operator i Employee (ktora operator zawiera) na OpeAuthResultDto
    public static OpeAuthResultDto of (Operator operator){
        OpeAuthResultDto dto = new OpeAuthResultDto();
        dto.setAuthenticated(true);
        dto.setFirstName(operator.getEmployee().getFirstName());
        dto.setLastName(operator.getEmployee().getLastName());
        dto.setIdOperator(operator.getIdOperator());
        return dto;
    }


}