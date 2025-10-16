package com.justkidding.www.controller;

import com.justkidding.www.configuration.SecurityConfig;
import com.justkidding.www.enums.Role;
import com.justkidding.www.model.User;
import com.justkidding.www.security.JwtAuthFilter;
import com.justkidding.www.service.UserService;
import com.justkidding.www.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Beni Samuel", "benisamuel566@gmail.com", "beni@ish", 798676159, Role.ADMIN);
    }

    @Test
    @WithMockUser(username = "benisamuel566@gmail.com", roles = {"ADMIN"})
    void getUserByIdTest() throws Exception {
        // Arrange
        Mockito.when(userService.getUserById(1L)).thenReturn(user);

        // Mock JWT validation
        Mockito.when(jwtUtil.validateToken(Mockito.anyString(), Mockito.any(UserDetails.class))).thenReturn(true);
        Mockito.when(jwtUtil.extractToken(Mockito.anyString())).thenReturn("benisamuel566@gmail.com");

        // Act & Assert
        mockMvc.perform(get("/api/justkidding/user/1")
                        .header("Authorization", "Bearer faketoken"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Beni Samuel"))
                .andExpect(jsonPath("$.data.email").value("benisamuel566@gmail.com"));

        Mockito.verify(userService, Mockito.times(1)).getUserById(1L);
    }
}
