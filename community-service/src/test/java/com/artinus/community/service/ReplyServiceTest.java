package com.artinus.community.service;

import com.artinus.community.domain.Post;
import com.artinus.community.domain.PostRepository;
import com.artinus.community.domain.Reply;
import com.artinus.community.domain.ReplyRepository;
import com.artinus.community.service.model.ReplyDto;
import com.artinus.community.service.model.ReplySearchCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceTest {
    @Autowired ReplyService replyService;
    @Autowired PostRepository postRepository;
    @Autowired ReplyRepository replyRepository;

    @Test
    void writeReply() {
        //given
        Post savedPost = postRepository.save(Post.newInstance("제목", "본문", "teser@ggg.gg"));

        String postId = savedPost.getId().toString();

        Reply rootReply = replyRepository.save(Reply.newInstance("내용~", "tester", savedPost));
        ReplyDto leafReply1 = ReplyDto.builder()
                .comment("내용~~~")
                .email("tester")
                .parentReplyId(rootReply.getId().toString())
                .build();

        ReplyDto leafReply2 = ReplyDto.builder()
                .comment("내용~~~")
                .email("tester")
                .parentReplyId(rootReply.getId().toString())
                .build();

        ReplyDto leafReply3 = ReplyDto.builder()
                .comment("내용~~~")
                .email("tester")
                .parentReplyId(rootReply.getId().toString())
                .build();

        //when

        replyService.writeReply(postId, leafReply1);
        replyService.writeReply(postId, leafReply2);
        replyService.writeReply(postId, leafReply3);

        //then

    }

    @Test
    void getReplies() {
        //given
        Post savedPost = postRepository.save(Post.newInstance("제목", "본문", "teser@ggg.gg"));
        Reply rootReply = replyRepository.save(Reply.newInstance("내용~", "tester", savedPost));
        replyRepository.save(Reply.newInstance("내용~", "tester", savedPost));
        replyRepository.save(Reply.newInstance("내용~", "tester", savedPost));


        //when
        Page<Reply> replies =
                replyService.getReplies(new ReplySearchCriteria(savedPost.getId().toString(), rootReply.getId().toString(), 0));

        //then
        assertNotNull(replies.getContent());
    }
}