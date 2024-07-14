package com.repository;

import com.exception.RepositoryException;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName) throws RepositoryException;

    Optional<User> findByEmail(String email) throws RepositoryException;

    Optional<User> findByToken(String token) throws RepositoryException;

    Optional<User> findUserByUserId(long userId) throws RepositoryException;

    @Query("SELECT u.userId FROM User u WHERE u.userName = :userName")
    Optional<Long> findUserIdByUserName(@Param("userName") String userName) throws RepositoryException;

}
