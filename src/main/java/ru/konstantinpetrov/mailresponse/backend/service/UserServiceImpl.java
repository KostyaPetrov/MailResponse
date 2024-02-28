package ru.konstantinpetrov.mailresponse.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    
}
