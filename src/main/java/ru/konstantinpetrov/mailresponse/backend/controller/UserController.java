package ru.konstantinpetrov.mailresponse.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.konstantinpetrov.mailresponse.backend.dtoLayer.GetUserDTO;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseEnterDTO;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseUserDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.BlockStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionService;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;



@RestController
public class UserController {


    private UserService userService;

    private QuestionService questionService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
    
    	
	@PostMapping(path="/addUser")
	public ResponseEntity<ResponseEnterDTO> addUser(@RequestBody GetUserDTO userDTO) {
		try {
            System.out.println("Controller get User: " + userDTO);
            User user= new User();
            user.setName(user.getName());
            user.setPassword(user.getPassword());
            user.setRole(Roles.USER);
            user.setBlockStatus(BlockStatus.FREE);
			userService.addUser(user);
			
			return new ResponseEntity<>(new ResponseEnterDTO(true),
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseEnterDTO(false),
                    HttpStatus.BAD_REQUEST);
        }
	}

    @PostMapping(path="/addModerator")
	public ResponseEntity<ResponseEnterDTO> addModerator(@RequestBody GetUserDTO userDto) {
		try {
            System.out.println("Controller get User: " + userDto);
            User user= new User();
            user.setName(user.getName());
            user.setPassword(user.getPassword());
            user.setRole(Roles.MODERATOR);
			userService.addUser(user);
			
			return new ResponseEntity<>(new ResponseEnterDTO(true),
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseEnterDTO(false),
                    HttpStatus.BAD_REQUEST);
        }
	}

    @PostMapping(path="/changeBlockStatus/{changerId}/{userId}")
	public ResponseEntity<ResponseEnterDTO> changeBlockStatus(@PathVariable Long changerId, @PathVariable Long userId) {
		try {
            Roles role=userService.getUserRole(changerId);
            if(role!=Roles.MODERATOR){
                throw new Exception();    
            }
			userService.changeBlockStatus(userId);
            
			
			return new ResponseEntity<>(new ResponseEnterDTO(true),
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseEnterDTO(false),
                    HttpStatus.BAD_REQUEST);
        }
	}

    @GetMapping(path="/findUser/{name}")
	public ResponseEntity<ResponseUserDTO> findUser(@PathVariable String name) {
		try {
            System.out.println("User Controller get name " + name +" for find information");
			User user = this.userService.findUser(name);
            List<Long> listQuestionId = this.questionService.getIdQuestion(user.getId());   

			return new ResponseEntity<>(new ResponseUserDTO(user.getId(), listQuestionId, "User was found succes"),
                 HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseUserDTO(0, null, "User not found"),
                    HttpStatus.BAD_REQUEST);
        }
	}
}
