package com.example.bookstorebe.repository;

import com.example.bookstorebe.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    User getUserByUsername(String username);

    boolean existsByUsername(String name);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

//    Integer getBookId();
}
