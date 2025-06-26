package com.justkidding.www.controller;

import com.justkidding.www.dto.PostDto;
import com.justkidding.www.model.Post;
import com.justkidding.www.service.PostService;
import com.justkidding.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/justkidding/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all posts!!! 🎉🎉🎉", this.postService.getAllPost()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> getPostById (@PathVariable Long id) {
        Post post = this.postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained a post!!! 🎉🎉🎉", post));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain a post not found!!! 😔💔💔", null));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Post>> createPost (@RequestPart PostDto postDto, @RequestPart MultipartFile file) throws IOException {
        Post post = this.postService.createPost(postDto, file);
        if (post != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Successfully created post!!! 🎉🎉🎉", post));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to create a post bad request!!! 🎉🎉🎉", null));
    }
}
