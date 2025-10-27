package com.justkidding.www.service;

import com.justkidding.www.enums.Role;
import com.justkidding.www.enums.Status;
import com.justkidding.www.model.Challenge;
import com.justkidding.www.repository.ChallengeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.justkidding.www.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {
    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ChallengeService challengeService;

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
        authorOne = new User ("AdminOne", "adminone@gmail.com", "admin1234", 798676159, Role.ADMIN );
        memberOne = new User ("MemberOne", "memberone@gmail.com", "member1234", 798676159, Role.USER );

        challenge1 = new Challenge ("Merge student management system", "Combine branches of the student management system", authorOne, Status.OPENED, LocalDateTime.now(), LocalDateTime.now() );
        challenge2 = new Challenge ("Pull student management system", "pull ahead branch of the student management system", memberOne, Status.OPENED, LocalDateTime.now(), LocalDateTime.now() );
        challenges = new ArrayList<Challenge>();

        challenges.add(challenge1);
        challenges.add(challenge2);
    }

    @Test
    @DisplayName("Getting All Challenges - returning list of challenges")
    void testGetAllChallenges () {
        // Arrange
        Mockito.when(challengeRepository.findAll()).thenReturn(challenges);

        // Act
        List<Challenge> obtained_challenges = challengeService.getAllChallenge();

        // Assert & Verify
        Assertions.assertNotNull(obtained_challenges);
        Assertions.assertEquals(2, obtained_challenges.size());

        Mockito.verify(challengeRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Getting Challenge - returning challenge")
    void testGetChallengeSuccessfully () {
        // Arrange
        Mockito.when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge1));

        // Act
        Challenge challenge_result = challengeService.getChallengeById(1L);

        // Assert
        Assertions.assertNotNull(challenge_result);
        Assertions.assertEquals("Merge student management system", challenge_result.getName());

        // Verify
        Mockito.verify(challengeRepository, Mockito.times(1)).findById(1L);
    }
}
