package com.artinus.community.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
@NoArgsConstructor
public class ReplyDto {
    private String commentId;
    private String comment;
    private String parentReplyId;
    private List<ReplyDto> childReplies;
    private String email;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    public ReplyDto(UUID postId, UUID commentId, String comment, String parentReplyId, List<ReplyDto> childReplies, String email, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.commentId = commentId.toString();
        this.comment = comment;
        this.parentReplyId = parentReplyId;
        this.childReplies = childReplies;
        this.email = email;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }

    @Builder
    public ReplyDto(String postId, String commentId, String comment, String parentReplyId, List<ReplyDto> childReplies, String email, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.commentId = commentId;
        this.comment = comment;
        this.parentReplyId = parentReplyId;
        this.childReplies = childReplies;
        this.email = email;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
}
