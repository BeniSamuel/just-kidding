package com.justkidding.www.service;

import com.justkidding.www.dto.ChallengeDto;
import com.justkidding.www.model.Challenge;
import com.justkidding.www.model.User;
import com.justkidding.www.repository.ChallengeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final UserService userService;

    public Challenge createChallenge (ChallengeDto challengeDto) {
        User user = this.userService.getUserById(challengeDto.getUser_id());
        if (user != null) {
            Challenge newChallenge = new Challenge(challengeDto.getName(), challengeDto.getDescription(), user, challengeDto.getStatus(), challengeDto.getStart_time(), challengeDto.getEnd_time());
            return this.challengeRepository.save(newChallenge);
        }
        return null;
    }

    public List<Challenge> getAllChallenge () {
        return this.challengeRepository.findAll();
    }

    public List<Challenge> getAllChallengeByUser (Long user_id) {
        User user = this.userService.getUserById(user_id);
        if (user != null) {
            return this.challengeRepository.getChallengeByAuthor(user);
        }
        return null;
    }

    public Challenge getChallengeById (Long id) {
        return this.challengeRepository.findById(id).orElse(null);
    }

    public Page<Challenge> searchChallengeByName (int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(name));
        return this.challengeRepository.findAll(pageable);
    }

    public Challenge updateChallengeById (Long id, ChallengeDto challengeDto) {
        Challenge challenge = this.getChallengeById(id);
        if (challenge == null) {
            return null;
        }

        User user = this.userService.getUserById(challengeDto.getUser_id());
        if (user == null) {
            return null;
        }

        challenge.setName(challengeDto.getName());
        challenge.setDescription(challengeDto.getDescription());
        challenge.setAuthor(user);
        challenge.setChallenge_status(challengeDto.getStatus());
        challenge.setStart_time(challengeDto.getStart_time());
        challenge.setEnd_time(challengeDto.getEnd_time());

        return this.challengeRepository.save(challenge);
    }

    public Boolean deleteChallengeById (Long id) {
        Challenge challenge = this.getChallengeById(id);
        if (challenge != null) {
            this.challengeRepository.delete(challenge);
            return true;
        }
        return false;
    }

}
