package com.justkidding.www.service;

import com.justkidding.www.dto.CommentDto;
import com.justkidding.www.model.Comment;
import com.justkidding.www.model.Post;
import com.justkidding.www.model.User;
import com.justkidding.www.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public List<Comment> getAllComments () {
        return this.commentRepository.findAll();
    }

    public Comment getCommentById (Long id) {
        return this.commentRepository.getCommentById(id).orElse(null);
    }

    public List<Comment> getCommentByUser (Long user_id) {
        User user = this.userService.getUserById(user_id);
        if (user != null) {
            return this.commentRepository.getCommentsByAuthor(user);
        }
        return null;
    }

    public List<Comment> getCommentByPost (Long post_id) {
        Post post = this.postService.getPostById(post_id);
        if (post != null) {
            return this.commentRepository.getCommentsByPost(post);
        }
        return null;
    }

    public Comment createComment (CommentDto commentDto) {
        User author = this.userService.getUserById(commentDto.getUser_id());
        if (author == null) {
            return null;
        }

        Post post = this.postService.getPostById(commentDto.getPost_id());
        if (post == null) {
            return null;
        }

        Comment newComment = new Comment(commentDto.getStatement(), author, post);
        return this.commentRepository.save(newComment);
    }
}
