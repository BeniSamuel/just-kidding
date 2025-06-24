package com.justkidding.www.repository;

import com.justkidding.www.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById (Long id);
    Optional<User> getUserByEmail (String email);
}
