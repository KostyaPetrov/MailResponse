package ru.konstantinpetrov.mailresponse.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseEnterDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.Mark;
import ru.konstantinpetrov.mailresponse.backend.service.MarkService;

@RestController
public class MarkController {
    private MarkService markService;

    @Autowired
    public void setMarkService(MarkService markService) {
        this.markService = markService;
    }

    @PostMapping(path="/addMark")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
	public ResponseEntity<ResponseEnterDTO> addMark(@RequestBody Mark mark) {
		try {
            System.out.println(mark);
			markService.addMark(mark);
			
			return new ResponseEntity<>(new ResponseEnterDTO(true, "Успешно"),
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>(new ResponseEnterDTO(false, "Произошла ошибка"),
                    HttpStatus.BAD_REQUEST);
        }
	}
}
