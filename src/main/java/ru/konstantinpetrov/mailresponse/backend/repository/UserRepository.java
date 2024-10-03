package ru.konstantinpetrov.mailresponse.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.konstantinpetrov.mailresponse.backend.entity.BlockStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.User;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    User findByUserId(Long id);

    User save(User user);
    List<User> findAllByBlockStatus(BlockStatus blockStatus);
}
