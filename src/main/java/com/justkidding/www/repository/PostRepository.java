package com.justkidding.www.repository;

import com.justkidding.www.model.Post;
import com.justkidding.www.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> getPostById (Long id);
    List<Post> getPostsByAuthor (User user);
}
