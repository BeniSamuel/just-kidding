package com.justkidding.www.repository;

import com.justkidding.www.model.Challenge;
import com.justkidding.www.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> getChallengeByAuthor(User user);
}
