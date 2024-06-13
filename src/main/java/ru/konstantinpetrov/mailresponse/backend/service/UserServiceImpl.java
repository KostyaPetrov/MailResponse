package ru.konstantinpetrov.mailresponse.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.GetUserDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.BlockStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addUser(User user){
        try{
            System.out.println("Servise get user:");
            this.userRepository.save(user);
            System.out.println("User saved");
        }catch(Exception exception){
            System.out.println("Error: " + exception);
        }   
    }

    @Override
    public User findUser(String name) {
        try{
            System.out.println("User Controller get name " + name +" for find information");
            User user = this.userRepository.findByName(name);
            return user;
        }catch(Exception exception){
            System.out.println("Error: " + exception);
            throw new IllegalArgumentException(exception);
        }
    }

    @Override
    public Roles getUserRole(Long id){
        try{
            User user = this.userRepository.findByUserId(id);
            return user.getRole();
        }catch(Exception exception){
            System.out.println("Error: " + exception);
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    @Transactional
    public void changeBlockStatus(Long id){
        try{
            User user=userRepository.findByUserId(id);
            if(user.getBlockStatus()==BlockStatus.FREE){
                user.setBlockStatus(BlockStatus.BLOCKED);
            }else{
                user.setBlockStatus(BlockStatus.FREE);
            }
            userRepository.save(user);
        }catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public boolean authUser(GetUserDTO userDTO){
        try{
            User user = userRepository.findByName(userDTO.getName());
            
            if(passwordEncoder.matches(userDTO.getPassword(), user.getPassword())){
                return true;
            }else{
                return false;
            }
            
        }catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
            return false;
        }
    }

    
}
