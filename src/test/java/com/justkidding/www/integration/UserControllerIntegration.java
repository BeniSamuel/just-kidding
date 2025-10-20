package com.justkidding.www.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justkidding.www.dto.RegisterDto;
import com.justkidding.www.enums.Role;
import com.justkidding.www.model.User;
import com.justkidding.www.repository.UserRepository;
import com.justkidding.www.utils.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerIntegration {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;

    @BeforeEach
    void setUp () {
        userRepository.deleteAll();
        baseUrl = "http://localhost:" + port + "/api/justkidding/user";
    }

    @Test
    void testCreateUserIntegration () {
        RegisterDto registerDto = new RegisterDto("Beni Samuel", "benisamuel566@gmail.com", "beni@ish", Role.ADMIN, 798676159);

        // When
        ParameterizedTypeReference<ApiResponse<User>> typeRef = new ParameterizedTypeReference<ApiResponse<User>>() {};
        ResponseEntity<ApiResponse<User>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                new HttpEntity<>(registerDto),
                typeRef
        );
        // Then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        User user = response.getBody().data;
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("Beni Samuel", user.getName());
        Assertions.assertEquals("benisamuel566@gmail.com", user.getEmail());


        // Verifying in the database
        User savedUser = userRepository.getUserByEmail("benisamuel566@gmail.com").get();
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("Beni Samuel", savedUser.getName());
    }
}
