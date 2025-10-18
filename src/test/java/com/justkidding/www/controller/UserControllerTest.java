package com.justkidding.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justkidding.www.configuration.SecurityConfig;
import com.justkidding.www.dto.RegisterDto;
import com.justkidding.www.enums.Role;
import com.justkidding.www.model.User;
import com.justkidding.www.security.JwtAuthFilter;
import com.justkidding.www.service.UserService;
import com.justkidding.www.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Autowired
    private ObjectMapper objectMapper;

    private User user1;
    private User user2;
    private RegisterDto registerDto;

    @BeforeEach
    void setUp() {
        user1 = new User("Beni Samuel", "benisamuel566@gmail.com", "beni@ish", 798676159, Role.ADMIN);
        user2 = new User("Ishimwe Hirwa", "ishimwebeni544@gmail.com", "ish@123", 788836152, Role.USER);
        registerDto = new RegisterDto("Beni Samuel", "benisamuel566@gmail.com", "beni@ish", Role.ADMIN, 798676159);
    }

    @Test
    @WithMockUser(username = "benisamuel566@gmail.com", roles = {"ADMIN"})
    void getUserByIdTest() throws Exception {
        // Arrange
        Mockito.when(userService.getUserById(1L)).thenReturn(user1);

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

    @Test
    void testGettingAllUsersSuccessfully () throws Exception {
        // Arrange
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/justkidding/user/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2));

        Mockito.verify(userService, Mockito.times(1)).getAllUsers();
    }

    @Test
    void testingGettingNullUsers () throws Exception {
        Mockito.when(userService.getUserById(1L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/justkidding/user/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        Mockito.verify(userService, Mockito.times(1)).getUserById(1L);
    }

    @Test
    @DisplayName("/api/justkidding/user/update/{id} - testing updating user by id")
    void testUpdatingUserSuccessfully() throws Exception {
        // Arrange
        Mockito.when(userService.updateUserById(Mockito.eq(1L), Mockito.any(RegisterDto.class)))
                .thenReturn(user1);

        // Act & Assert
        mockMvc.perform(put("/api/justkidding/user/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Beni Samuel"))
                .andExpect(jsonPath("$.data.email").value("benisamuel566@gmail.com"));

        Mockito.verify(userService, Mockito.times(1)).updateUserById(Mockito.eq(1L), Mockito.any(RegisterDto.class));
    }


}
