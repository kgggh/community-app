package com.artinus.community.api.response;

import com.artinus.community.domain.Reply;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ReplyResponseDto {
    private String parentReplyId;
    private String replyId;
    private String comment;
    private String email;
    private List<ReplyResponseDto> childReplies;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/seoul")
    private LocalDateTime createdDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/seoul")
    private LocalDateTime modifiedDateTime;

    public ReplyResponseDto(Reply reply) {
        this.parentReplyId = reply.getParentReply() == null ? null : reply.getParentReply().getId().toString();
        this.replyId = reply.getId().toString();
        this.comment = reply.getComment();
        this.email = reply.getEmail();
        this.childReplies = getChildReplies(reply.getChildReplies());
        this.createdDateTime = reply.getCreatedDateTime();
        this.modifiedDateTime = reply.getModifiedDateTime();
    }

    private List<ReplyResponseDto> getChildReplies(List<Reply> replies) {
        return replies.stream().map(ReplyResponseDto::new).collect(Collectors.toList());
    }
}
