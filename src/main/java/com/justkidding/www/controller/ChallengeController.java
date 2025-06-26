package com.justkidding.www.controller;

import com.justkidding.www.dto.ChallengeDto;
import com.justkidding.www.model.Challenge;
import com.justkidding.www.service.ChallengeService;
import com.justkidding.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/justkidding/challenge")
@AllArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Challenge>>> getAllChallenge () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all challenges!!! 🎉🎉🎉", this.challengeService.getAllChallenge()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Challenge>> getChallengeById (@PathVariable Long id) {
        Challenge challenge = this.challengeService.getChallengeById(id);
        if (challenge != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained a challenge!!! 🎉🎉🎉", challenge));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to get challenge not found!!! 😔💔💔", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<Challenge>>> searchChallengeByName (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String name
    ) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all challenge with name", this.challengeService.searchChallengeByName(page, size, name)));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Challenge>> createChallenge (@RequestBody ChallengeDto challengeDto) {
        Challenge newChallenge = this.challengeService.createChallenge(challengeDto);
        if (newChallenge != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Successfully created a challenge!!! 🎉🎉🎉", newChallenge));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to create a challenge bad request!!! 😔💔💔", null));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Challenge>> updateChallengeById (@PathVariable Long id, @RequestBody ChallengeDto challengeDto) {
        Challenge challenge = this.challengeService.updateChallengeById(id, challengeDto);
        if (challenge != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully updated a challenge!!! 🎉🎉🎉", challenge));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(true, "Failed to update a challenge!!! 😔💔💔",null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteChallengeById (@PathVariable Long id) {
        return this.challengeService.deleteChallengeById(id) ?
               ResponseEntity.ok(new ApiResponse<>(true, "Successfully deleted a challenge!!! 🎉🎉🎉", true)) :
               ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to delete a challenge not found!!!", false));
    }
}
