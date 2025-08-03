package com.justkidding.www.service;

import com.justkidding.www.dto.LikeDto;
import com.justkidding.www.model.Like;
import com.justkidding.www.model.Post;
import com.justkidding.www.model.User;
import com.justkidding.www.repository.LikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    public List<Like> getAllLikes () {
        return this.likeRepository.findAll();
    }

    public Like getLikeById (Long id) {
        return this.likeRepository.findById(id).orElse(null);
    }

    public List<Like> getAllLikesByPost (Long post_id) {
        Post post = this.postService.getPostById(post_id);
        if (post != null) {
            return this.likeRepository.getLikesByPost(post);
        }
        return null;
    }

    public List<Like> getAllLikesByUser (Long user_id) {
        User user = this.userService.getUserById(user_id);
        if (user != null) {
            return this.likeRepository.getLikesByAuthor(user);
        }
        return null;
    }

    public Like createLike (LikeDto likeDto) {
        User user = this.userService.getUserById(likeDto.getUser_id());
        if (user == null) {
            return null;
        }

        Post post = this.postService.getPostById(likeDto.getPost_id());
        if (post == null) {
            return null;
        }

        Like newLike = new Like(user, post);
        return this.likeRepository.save(newLike);
    }

    public Boolean deleteLike (Long id) {
        Like like = this.getLikeById(id);
        if (like != null) {
            this.likeRepository.delete(like);
            return true;
        }
        return false;
    }
}
