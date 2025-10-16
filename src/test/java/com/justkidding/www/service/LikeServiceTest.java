package com.justkidding.www.service;

import com.justkidding.www.dto.LikeDto;
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

    @Test
    @DisplayName("Testing for creating a like")
    void createLikeTestShouldReturnLike() {
        // Arrange
        LikeDto newLikedto = new LikeDto(1L, 2L);
        User newUser = new User("Beni Samuel", "benisamuel566@gmail.com", "beni@ish", 798676159, Role.USER);
        Post newPost = new Post("Dancing", newUser, "moving", "nice work!!!");
        Like newLike = new Like(newUser, newPost);

        // Mock dependencies
        Mockito.when(userService.getUserById(newLikedto.getUser_id())).thenReturn(newUser);
        Mockito.when(postService.getPostById(newLikedto.getPost_id())).thenReturn(newPost);
        Mockito.when(likeRepository.save(Mockito.any(Like.class))).thenReturn(newLike);

        // Act
        Like returnedLike = likeService.createLike(newLikedto);

        // Assert
        Assertions.assertNotNull(returnedLike);
        Assertions.assertEquals(1, returnedLike.getCount());
        Assertions.assertEquals(newUser, returnedLike.getAuthor());
        Assertions.assertEquals(newPost, returnedLike.getPost());

        // Verify interactions
        Mockito.verify(userService, Mockito.times(1)).getUserById(1L);
        Mockito.verify(postService, Mockito.times(1)).getPostById(2L);
        Mockito.verify(likeRepository, Mockito.times(1)).save(Mockito.any(Like.class));
    }

}
