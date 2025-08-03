package com.justkidding.www.controller;

import com.justkidding.www.dto.LikeDto;
import com.justkidding.www.model.Like;
import com.justkidding.www.service.LikeService;
import com.justkidding.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/justkidding/like")
@AllArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Like>>> getAllLikes () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all likes", this.likeService.getAllLikes()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Like>> getLikeById (@PathVariable Long id) {
        Like like = this.likeService.getLikeById(id);
        if (like != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained like", like));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to get like", null));
    }

    @GetMapping("/post/{post_id}")
    public ResponseEntity<ApiResponse<List<Like>>> getLikeByPost (@PathVariable Long post_id) {
        List<Like> likes = this.likeService.getAllLikesByPost(post_id);
        if (!likes.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all like by post", likes));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain like by post", null));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Like>> createLike (@RequestBody LikeDto likeDto) {
        Like newLike = this.likeService.createLike(likeDto);
        if (newLike != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Successfully created like", newLike));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to create a like", null));
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<ApiResponse<List<Like>>> getLikeByUser (@PathVariable Long user_id) {
        List<Like> likes = this.likeService.getAllLikesByUser(user_id);
        if (!likes.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all like by user", likes));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain like by user", null));
    }
}
