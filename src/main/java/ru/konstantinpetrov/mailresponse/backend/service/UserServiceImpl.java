package ru.konstantinpetrov.mailresponse.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.konstantinpetrov.mailresponse.backend.entity.BlockStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user){
        try{
            System.out.println("Servise get user:");
            this.userRepository.save(user);
            System.out.println("User saved");
        }catch(Exception exception){
            throw new IllegalArgumentException("Client with current login is already exists!");
        }   
    }

    @Override
    public User findUser(String name) {
        try{
            User user = this.userRepository.findByName(name);
            return user;
        }catch(Exception exception){
            throw new IllegalArgumentException("This user is does not exists");
        }
    }

    @Override
    public Roles getUserRole(Long id){
        try{
            User user = this.userRepository.findByUserId(id);
            return user.getRole();
        }catch(Exception exception){
            throw new IllegalArgumentException("This user is does not exists");
        }
    }

    @Override
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
            throw new IllegalArgumentException("This user is does not exists");
        }
    }

    
}
