package com.justkidding.www.controller;

import com.justkidding.www.configuration.SecurityConfig;
import com.justkidding.www.enums.Role;
import com.justkidding.www.enums.Status;
import com.justkidding.www.model.Challenge;
import com.justkidding.www.model.User;
import com.justkidding.www.security.JwtAuthFilter;
import com.justkidding.www.service.ChallengeService;
import com.justkidding.www.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChallengeController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(SecurityConfig.class)
public class ChallengeControllerTest {
    @MockBean
    private ChallengeService challengeService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private JwtUtil jwtUtil;

    // User used
    private User authorOne;
    private User memberOne;

    // challenges used
    private Challenge challenge1;
    private Challenge challenge2;

    // List of challenges
    private List<Challenge> challenges;

    @BeforeEach()
    void setUp () {
        authorOne = new User("AdminOne", "adminone@gmail.com", "admin1234", 798676159, Role.ADMIN );
        memberOne = new User ("MemberOne", "memberone@gmail.com", "member1234", 798676159, Role.USER );

        challenge1 = new Challenge("Merge student management system", "Combine branches of the student management system", authorOne, Status.OPENED, LocalDateTime.now(), LocalDateTime.now() );
        challenge2 = new Challenge ("Pull student management system", "pull ahead branch of the student management system", memberOne, Status.OPENED, LocalDateTime.now(), LocalDateTime.now() );
        challenges = new ArrayList<Challenge>();

        challenges.add(challenge1);
        challenges.add(challenge2);
    }

    @Test
    @WithMockUser(username = "benisamuel566@gmail.com", roles = {"ADMIN"})
    void testGetAllChallenges () throws Exception {
        Mockito.when(challengeService.getAllChallenge()).thenReturn(challenges);

        // Act & Assert
        mockMvc.perform(get("/api/justkidding/challenge/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2));

        Mockito.verify(challengeService, Mockito.times(1)).getAllChallenge();
    }
}
