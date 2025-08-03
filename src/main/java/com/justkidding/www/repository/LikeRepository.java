package com.justkidding.www.repository;

import com.justkidding.www.model.Like;
import com.justkidding.www.model.Post;
import com.justkidding.www.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> getLikesByPost (Post post);
    List<Like> getLikesByAuthor (User user);
}
