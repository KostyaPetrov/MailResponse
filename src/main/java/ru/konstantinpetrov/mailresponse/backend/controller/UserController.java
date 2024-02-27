package ru.konstantinpetrov.mailresponse.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseEnterDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    }
    	
	@PostMapping(path="/addUser")
	public ResponseEntity<ResponseEnterDTO> addUser(@RequestBody User user) {
		try {
			userService.addUser(user);
			
			return new ResponseEntity<>(new ResponseEnterDTO(true),
                 HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseEnterDTO(false),
                    HttpStatus.BAD_REQUEST);
        }
	}

    @GetMapping(path="/findUser/{name}")
	public ResponseEntity<ResponseEnterDTO> findUser(@PathVariable String name) {
		try {
			userService.findUser(name);
			
			return new ResponseEntity<>(new ResponseEnterDTO(true),
                 HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseEnterDTO(false),
                    HttpStatus.BAD_REQUEST);
        }
	}
}
