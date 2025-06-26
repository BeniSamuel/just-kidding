package com.justkidding.www.service;

import com.justkidding.www.dto.PostDto;
import com.justkidding.www.model.Post;
import com.justkidding.www.model.User;
import com.justkidding.www.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final FileService fileService;

    public List<Post> getAllPost () {
        return this.postRepository.findAll();
    }

    public List<Post> getPostByAuthor (Long user_id) {
        User user = this.userService.getUserById(user_id);
        if (user != null) {
            return this.postRepository.getPostsByAuthor(user);
        }
        return null;
    }

    public Post getPostById (Long id) {
        return this.postRepository.getPostById(id).orElse(null);
    }

    public Post createPost (PostDto postDto, MultipartFile file) throws IOException {
        User author = this.userService.getUserById(postDto.getUser_id());
        if (author == null) {
            return null;
        }

        String file_path = fileService.uploadFile(file);
        return this.postRepository.save(new Post(file.getOriginalFilename(), author, file_path, postDto.getDescription()));
    }

    public Post updatePostById (Long id, PostDto postDto, MultipartFile file) throws IOException {
        Post post = this.getPostById(id);
        if (post == null) {
            return null;
        }

        User user = this.userService.getUserById(postDto.getUser_id());
        if (user == null) {
            return null;
        }

        String file_path = fileService.uploadFile(file);
        post.setAuthor(user);
        post.setTitle(file.getOriginalFilename());
        post.setDescription(post.getDescription());
        post.setFile_path(file_path);

        return this.postRepository.save(post);
    }

}
