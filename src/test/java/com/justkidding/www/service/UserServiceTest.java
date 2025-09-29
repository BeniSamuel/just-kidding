package com.justkidding.www.service;

import com.justkidding.www.enums.Role;
import com.justkidding.www.model.User;
import com.justkidding.www.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Getting user by id")
    void getUserByIdTestShouldObtainUserSuccessfully () {
        // Arrange
        User user = new User("Beni Samuel", "benisamuel566@gmail.com", "beni@ish123", 798676159, Role.ADMIN);
        Mockito.when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));

        // Act
        User userfound = userService.getUserById(1L);

        // Assert
        Assertions.assertNotNull(userfound);
        Assertions.assertEquals("Beni Samuel", userfound.getName());
        Mockito.verify(userRepository, Mockito.times(1)).getUserById(1L);
    }

    @Test
    @DisplayName("Testing returning null user")
    void getUserByIdShouldReturnNull () {
        // Arrange
        Mockito.when(userRepository.getUserById(1L)).thenReturn(Optional.ofNullable(null));

        // Act
        User user = userService.getUserById(1L);

        // Assert
        Assertions.assertNull(user);
        Mockito.verify(userRepository, Mockito.times(1)).getUserById(1L);
    }

    @Test
    @DisplayName("Testing getting user by email")
    void getUserByEmailShouldReturnUser () {
        // Arrange
        User user = new User("Beni Samuel", "benisamuel566@gmail.com", "beni@ish", 798676159, Role.ADMIN);
        Mockito.when(userRepository.getUserByEmail("benisamuel566@gmail.com")).thenReturn(Optional.of(user));

        // Act
        User user_found = userService.getUserByEmail("benisamuel566@gmail.com");

        // Assert
        Assertions.assertNotNull(user_found);
        Assertions.assertEquals("Beni Samuel", user_found.getName());
        Mockito.verify(userRepository, Mockito.times(1)).getUserByEmail("benisamuel566@gmail.com");
    }

    @Test
    @DisplayName("Testing user not found")
    void getUserByEmailTestShouldReturnNull () {
        // Arrange
        Mockito.when(userRepository.getUserByEmail("benisamuel566@gmail.com")).thenReturn(Optional.ofNullable(null));

        // Act
        User user = userService.getUserByEmail("benisamuel566@gmail.com");

        // Assert
        Assertions.assertNull(user);
        Mockito.verify(userRepository, Mockito.times(1)).getUserByEmail("benisamuel566@gmail.com");
    }

    @Test
    @DisplayName("Test all users")
    void getAllUsersTestShouldReturnUserList () {
        // Arrange
        User user1 = new User("Beni Samuel", "benisamuel566@gmail.com", "beni@ish", 798676159, Role.USER);
        User user2 = new User("John Alexis", "john@gmail.com", "john1234", 798676159, Role.USER);
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> obtained_users = userService.getAllUsers();

        // Assert
        Assertions.assertNotNull(obtained_users);
        Assertions.assertEquals(2, obtained_users.size());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }
}