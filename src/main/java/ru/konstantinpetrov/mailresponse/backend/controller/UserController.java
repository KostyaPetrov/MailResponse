package ru.konstantinpetrov.mailresponse.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    
    @Transactional
	@PostMapping(path="/addUser")
    @PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<ResponseEnterDTO> addUser(@RequestBody GetUserDTO userDTO) {
		try {
            System.out.println("Controller get User: " + userDTO);
            User user= new User();
            user.setName(userDTO.getName());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRole(Roles.USER);
            user.setBlockStatus(BlockStatus.FREE);
			userService.addUser(user);
			
			return new ResponseEntity<>(new ResponseEnterDTO(true, "Успешно"),
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>(new ResponseEnterDTO(false, "Произошла ошибка"),
                    HttpStatus.BAD_REQUEST);
        }
	}
    @Transactional
    @PostMapping(path="/addModerator")
    @PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<ResponseEnterDTO> addModerator(@RequestBody GetUserDTO userDTO) {
		try {
            System.out.println(userDTO);
            System.out.println("Controller get User: " + userDTO);
            User user= new User();
            user.setName(userDTO.getName());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRole(Roles.MODERATOR);
            user.setBlockStatus(BlockStatus.FREE);
			userService.addUser(user);
			
			return new ResponseEntity<>(new ResponseEnterDTO(true, "Успешно"),
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>(new ResponseEnterDTO(false, "Произошла ошибка"),
                    HttpStatus.BAD_REQUEST);
        }
	}
    @Transactional
    @PostMapping(path="/changeBlockStatus/{changerId}/{userId}")
    @PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<ResponseEnterDTO> changeBlockStatus(@PathVariable Long changerId, @PathVariable Long userId) {
		try {
            System.out.println(changerId + " " + userId);
            Roles role=userService.getUserRole(changerId);
            if(role!=Roles.MODERATOR){
                return new ResponseEntity<>(new ResponseEnterDTO(false, "Пользователь является модератором"),
                                    HttpStatus.BAD_REQUEST);
            }
			userService.changeBlockStatus(userId);
            
			
			return new ResponseEntity<>(new ResponseEnterDTO(true, "Успешно"),
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>(new ResponseEnterDTO(false, "Произошла ошибка"),
                    HttpStatus.BAD_REQUEST);
        }
	}

    @GetMapping(path="/findUser/{name}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
	public ResponseEntity<ResponseUserDTO> findUser(@PathVariable String name) {
		try {
            System.out.println("User Controller get name " + name +" for find information");
			User user = this.userService.findUser(name);
            List<Long> listQuestionId = this.questionService.getIdQuestion(user.getUserId());   

			return new ResponseEntity<>(new ResponseUserDTO(user.getUserId(), listQuestionId, "User was found succes"),
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>(new ResponseUserDTO(0, null, "User not found"),
                    HttpStatus.BAD_REQUEST);
        }
	}

    @PostMapping("/authUser")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<ResponseEnterDTO> authUser(@RequestBody GetUserDTO userDTO) {
        try{
            System.out.println(userDTO);
            userService.authUser(userDTO);
            return new ResponseEntity<>(new ResponseEnterDTO(true, "Успешно"),
                 HttpStatus.OK);
        }catch(Exception e){
            System.out.println("Error: " + e);
            return new ResponseEntity<>(new ResponseEnterDTO(false, "Произошла ошибка"),
                    HttpStatus.BAD_REQUEST);
        }
    }
    
}
