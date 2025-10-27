package com.justkidding.www.repository;

import com.justkidding.www.enums.Role;
import com.justkidding.www.enums.Status;
import com.justkidding.www.model.Challenge;
import com.justkidding.www.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@DataJpaTest
@ActiveProfiles("test")
public class ChallengeRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Test
    public void testSaveAndFindChallenge() {
        // Arrange — Create and persist a User first
        User author = new User(
                "Beni Samuel",
                "benisamuel566@gmail.com",
                "beni@ish",
                798676159,
                Role.ADMIN
        );
        entityManager.persist(author);

        // Act — Create and save a Challenge
        Challenge challenge = new Challenge(
                "Studying Physics",
                "I will start studying physics",
                author,
                Status.OPENED,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Challenge savedChallenge = challengeRepository.save(challenge);
        entityManager.flush();

        // Assert — verify persistence
        Assertions.assertNotNull(savedChallenge.getId(), "Challenge ID should not be null after saving");
        Assertions.assertEquals("Studying Physics", savedChallenge.getName());
        Assertions.assertEquals(Status.OPENED, savedChallenge.getChallenge_status());
        Assertions.assertEquals(author.getEmail(), savedChallenge.getAuthor().getEmail());
    }
}
