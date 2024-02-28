package ru.konstantinpetrov.mailresponse.backend.service;

import ru.konstantinpetrov.mailresponse.backend.entity.User;

public interface UserService {
    void addUser(User user);

    User findUser(String name);
}
