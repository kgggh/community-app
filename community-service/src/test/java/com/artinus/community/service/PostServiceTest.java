package com.artinus.community.service;

import com.artinus.community.domain.Post;
import com.artinus.community.domain.PostRepository;
import com.artinus.community.exception.PostCommonException;
import com.artinus.community.infra.ResponseUser;
import com.artinus.community.infra.UserServiceFeignClient;
import com.artinus.community.service.model.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {
    @Autowired PostRepository postRepository;
    @Autowired PostService postService;
    @MockBean UserServiceFeignClient userServiceFeignClient;

    @Test
    void writePost() {
        PostDto writePostDto = PostDto.builder()
                .email("tester")
                .title("제목~~")
                .description("본문~")
                .build();

        given(userServiceFeignClient.getUserInfo("tester"))
                .willReturn(new ResponseUser(UUID.randomUUID().toString(), "tester"));

        //when
        String postId = postService.writePost(writePostDto);

        //then
        assertNotNull(postId);
    }

    @Test
    void modifyPost() {
        //given
        Post post = postRepository.save(Post.newInstance("제목", "내용", "email"));
        PostDto modifyPostDto = PostDto.builder()
                .title("수정 제목~~~~~")
                .description("수정내용~")
                .build();

        PostDto emptyTitlePostDto = PostDto.builder()
                .title("")
                .description("수정내용~")
                .build();

        PostDto emptyDescriptionPostDto = PostDto.builder()
                .title("수정 제목~~~~~")
                .description("")
                .build();

        //when
        postService.modifyPost(post.getId().toString(), modifyPostDto);
        assertThrows(PostCommonException.class, () -> postService.modifyPost(post.getId().toString(), emptyTitlePostDto));
        assertThrows(PostCommonException.class, () -> postService.modifyPost(post.getId().toString(), emptyDescriptionPostDto));

        //then
    }

    @Test
    void removePost() {
        //given
        Post post = postRepository.save(Post.newInstance("제목", "내용", "email"));

        //when
        postService.removePost(post.getId().toString());

        //then
    }

    @Test
    void getPosts() {
        //given
        Post post = postRepository.save(Post.newInstance("제목", "내용", "email"));
        String failurePostId = "notExist";

        //when
        Post findPost = postService.getPost(post.getId().toString());
        assertThrows(RuntimeException.class, () -> postService.getPost(failurePostId));

        //then
        assertNotNull(findPost);
    }

    @Test
    void getPost() {
        //given
        Post post = postRepository.save(Post.newInstance("제목", "내용", "email"));

        //when
        Post findPost = postService.getPost(post.getId().toString());

        //then
        assertNotNull(findPost);
    }

}