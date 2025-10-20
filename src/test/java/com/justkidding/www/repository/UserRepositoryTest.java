package com.justkidding.www.repository;

import com.justkidding.www.enums.Role;
import com.justkidding.www.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindUser () {
        // Arrange & Act
        User savedUser = userRepository.save(new User("Beni Samuel", "benisamuel566@gmail.com", "beni@ish", 798676159, Role.ADMIN));
        entityManager.flush();

        // Assert
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("Beni Samuel", savedUser.getName());
        Assertions.assertEquals("benisamuel566@gmail.com", savedUser.getEmail());
    }

    @Test
    public void testSaveAndFindUserByEmail () {
        // Arrange & Act
        User user1 = userRepository.save(new User("Beni Samuel", "benisamuel566@gmail.com", "beni@ish", 798676159, Role.ADMIN));
        entityManager.flush();

        Optional<User> user_found = userRepository.getUserByEmail("benisamuel566@gmail.com");
        Assertions.assertNotNull(user_found.get());
        Assertions.assertEquals("Beni Samuel", user_found.get().getName());
    }
}
