package com.artinus.community.api;

import com.artinus.community.api.request.PostWriteRequestDto;
import com.artinus.community.api.request.ReplyWriteRequestDto;
import com.artinus.community.api.response.ReplyResponseDto;
import com.artinus.community.api.response.PostResponseDto;
import com.artinus.community.api.response.ResponseVO;
import com.artinus.community.domain.Post;
import com.artinus.community.domain.Reply;
import com.artinus.community.service.PostService;
import com.artinus.community.service.ReplyService;
import com.artinus.community.service.model.ReplyDto;
import com.artinus.community.service.model.ReplySearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping(path = "/community-service")
@RestController
public class CommunityController {
    private final PostService postService;
    private final ReplyService replyService;

    @GetMapping("/health")
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok("lived community-service !!");
    }

    @GetMapping("/posts/{post_id}")
    public ResponseEntity<Object> getPost(@PathVariable(name = "post_id") String postId) {
        Post post = postService.getPost(postId);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        ResponseVO<PostResponseDto> result = ResponseVO.success(postResponseDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/posts")
    public ResponseEntity<Object> getPosts() {
        List<Post> posts = postService.getPosts();
        List<PostResponseDto> postResponseDto =
                posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
        ResponseVO<List<PostResponseDto>> result = ResponseVO.success(postResponseDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/posts")
    public ResponseEntity<Object> writePost(@RequestBody PostWriteRequestDto postWriteRequestDto) {
        String postId = postService.writePost(postWriteRequestDto.toDto());
        return ResponseEntity.created(URI.create(String.format("/community-service/posts/%s", postId)))
                .build();
    }

    @DeleteMapping("/posts/{post_id}")
    public ResponseEntity<Object> removePost(@PathVariable(name = "post_id") String postId) {
        postService.removePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{post_id}/replies")
    public ResponseEntity<Object> getReplies(@PathVariable(name = "post_id") String postId,
                                             @RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) String replyId) {
        Page<Reply> replies =
                replyService.getReplies(new ReplySearchCriteria(postId, replyId, page));
        List<ReplyResponseDto> replyResponseDto =
                replies.stream().map(ReplyResponseDto::new).collect(Collectors.toList());
        ResponseVO<List<ReplyResponseDto>> result = ResponseVO.success(replyResponseDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/posts/{post_id}/replies")
    public ResponseEntity<Object> writeReply(@PathVariable(name = "post_id") String postId,
                                             @RequestBody ReplyWriteRequestDto replyWriteRequestDto) {
        ReplyDto replyDto = replyWriteRequestDto.toDto();
        String replyId = replyService.writeReply(postId, replyDto);
        return ResponseEntity.created(URI.create(String.format("/community-service/replies/%s", replyId)))
                .build();
    }
}
