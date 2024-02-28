package ru.konstantinpetrov.mailresponse.backend.dtoLayer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUserDTO {
    private long userId;
    private List<Long> questionId;
    private String inform;
}
