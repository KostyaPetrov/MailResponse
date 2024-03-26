package ru.konstantinpetrov.mailresponse.backend.dtoLayer;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserDTO {
    private String name;
    private String password;
}
