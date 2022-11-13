package com.artinus.community.domain;

import com.artinus.community.service.model.ReplySearchCriteria;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Disabled("querydsl test skip")
@SpringBootTest
@ActiveProfiles("test")
class ReplyRepositoryTest {

    @Autowired PostRepository postRepository;
    @Autowired ReplyRepository replyRepository;

    @Test
    void 댓글페이징시_N_플러스_1_테스트() {
        //given
        Post post = postRepository.save(Post.newInstance("게시글", "본문", "tester"));
        replyRepository.save(Reply.newInstance("댓글", "email", post));
        replyRepository.save(Reply.newInstance("댓글2", "email", post));
        replyRepository.save(Reply.newInstance("댓글3", "email", post));
        replyRepository.save(Reply.newInstance("댓글4", "email", post));
        replyRepository.save(Reply.newInstance("댓글5", "email", post));
        Reply reply = replyRepository.save(Reply.newInstance("댓글6", "email", post));

        //when
        replyRepository.findByPostAndComment(new ReplySearchCriteria(post.getId().toString(), reply.getId().toString(),0));
    }
}