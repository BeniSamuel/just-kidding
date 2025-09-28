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
}