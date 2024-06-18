package ru.konstantinpetrov.mailresponse.backend.dtoLayer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserDTO {
    private String name;
    private String password;
    private String email;
}
