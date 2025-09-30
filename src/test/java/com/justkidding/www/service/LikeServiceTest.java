package com.justkidding.www.service;

import com.justkidding.www.enums.Role;
import com.justkidding.www.model.Like;
import com.justkidding.www.model.Post;
import com.justkidding.www.model.User;
import com.justkidding.www.repository.LikeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {
    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    @Test
    @DisplayName("Getting all likes")
    void getAllLikesTestShouldReturnLikes () {
        // Arrange
        User newUser = new User("Beni Samuel", "benisamuel566@gmail.com", "beni@ish", 798676159, Role.USER);
        Post newPost = new Post("Dancing", newUser, "moving", "nice work!!!");

        Like like1 = new Like(newUser, newPost);
        Like like2 = new Like(newUser, newPost);
        List<Like> likes = new ArrayList<Like>();

        likes.add(like1);
        likes.add(like2);
        Mockito.when(likeRepository.findAll()).thenReturn(likes);

        // Act
        List<Like> likes_data = likeService.getAllLikes();

        // Assert
        Assertions.assertEquals(2, likes_data.size());
        Mockito.verify(likeRepository, Mockito.times(1)).findAll();
    }
}
