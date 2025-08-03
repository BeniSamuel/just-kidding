package com.justkidding.www.controller;

import com.justkidding.www.dto.CommentDto;
import com.justkidding.www.model.Comment;
import com.justkidding.www.service.CommentService;
import com.justkidding.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/justkidding/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Comment>>> getAllComments () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all comments", this.commentService.getAllComments()));
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<ApiResponse<List<Comment>>> getCommentByUser (@PathVariable Long user_id) {
        List<Comment> comments = this.commentService.getCommentByUser(user_id);
        if (!comments.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Success obtained all comment by user!!!", comments));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain comment by user!!!", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Comment>> getCommentById (@PathVariable Long id) {
        Comment comment = this.commentService.getCommentById(id);
        if (comment != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Successfully obtained a comment!!!", comment));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain comment", null));
    }

    @GetMapping("/post/{post_id}")
    public ResponseEntity<ApiResponse<List<Comment>>> getCommentByPost (@PathVariable Long post_id) {
        List<Comment> comments = this.commentService.getCommentByPost(post_id);
        if (!comments.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Success obtained all comment by user!!!", comments));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain comment by post!!!", null));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Comment>> createComment (@RequestBody CommentDto commentDto) {
        Comment newComment = this.commentService.createComment(commentDto);
        if (newComment != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Successfully created a new comment!!!", newComment));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to create a comment", null));
    }
}
