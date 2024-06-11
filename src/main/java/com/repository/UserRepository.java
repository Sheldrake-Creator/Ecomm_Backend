package com.repository;

import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>  findByUserName(String userName);

    User findByEmail(String email);

    User findByToken(String token);

    User findUserById(long userId);

}
