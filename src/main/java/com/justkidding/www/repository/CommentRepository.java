package com.justkidding.www.repository;

import com.justkidding.www.model.Comment;
import com.justkidding.www.model.Post;
import com.justkidding.www.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> getCommentById (Long id);
    List<Comment> getCommentsByAuthor (User author);
    List<Comment> getCommentsByPost (Post post);
}
