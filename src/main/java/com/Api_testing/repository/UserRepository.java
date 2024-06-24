package com.Api_testing.repository;

import com.Api_testing.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByNameAndPassword(String name, String password);
    // You can add custom query methods here if needed
}
