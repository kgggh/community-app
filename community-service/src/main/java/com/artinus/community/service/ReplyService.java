package com.artinus.community.service;

import com.artinus.community.exception.ReplyCommonException;
import com.artinus.community.service.model.ReplySearchCriteria;
import com.artinus.community.domain.Post;
import com.artinus.community.domain.Reply;
import com.artinus.community.domain.ReplyRepository;
import com.artinus.community.service.model.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final PostService postService;
    private final ReplyRepository replyRepository;

    @Transactional
    public String writeReply(String postId, ReplyDto replyDto) {
        Post post = postService.getPost(postId);
        Reply reply = Reply.newInstance(replyDto.getComment(), replyDto.getEmail(), post);
        if( !Strings.isBlank(replyDto.getParentReplyId()) ) {
            Reply parentReply = replyRepository.findById(UUID.fromString(replyDto.getParentReplyId())).orElseThrow(()
                    -> new ReplyCommonException("Not found reply"));
            reply.addReply(parentReply);
        }
        return replyRepository.save(reply).getId().toString();
    }

    @Transactional(readOnly = true)
    public Page<Reply> getReplies(ReplySearchCriteria replySearchCriteria) {
        return replyRepository.findByPostAndComment(replySearchCriteria);
    }
}
