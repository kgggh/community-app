package com.artinus.community.service;

import com.artinus.community.domain.Post;
import com.artinus.community.domain.PostRepository;
import com.artinus.community.exception.PostCommonException;
import com.artinus.community.infra.UserServiceFeignClient;
import com.artinus.community.service.model.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserServiceFeignClient userServiceFeignClient;

    public PostService(PostRepository postRepository, UserServiceFeignClient userServiceFeignClient) {
        this.postRepository = postRepository;
        this.userServiceFeignClient = userServiceFeignClient;
    }


    public String writePost(PostDto postDto) {
        String userId = userServiceFeignClient.getUserInfo(postDto.getEmail()).getUserId();
        Post writePost = Post.newInstance(postDto.getTitle(), postDto.getDescription(), userId);
        return postRepository.save(writePost).getId().toString();
    }

    public void modifyPost(String postId, PostDto postDto) {
        Post post = existPost(postId);
        post.modify(postDto.getTitle(), postDto.getDescription());
    }

    public void removePost(String postId) {
        postRepository.deleteById(UUID.fromString(postId));
    }

    public List<Post> getPosts() {
       return postRepository.findAll();
    }

    public Post getPost(String postId) {
        return existPost(postId);
    }

    private Post existPost(String postId) {
        return postRepository.findById(UUID.fromString(postId)).orElseThrow(()
                -> new PostCommonException("Not found post"));
    }
}
