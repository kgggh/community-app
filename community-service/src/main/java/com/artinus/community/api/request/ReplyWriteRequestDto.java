package com.artinus.community.api.request;

import com.artinus.community.service.model.ReplyDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyWriteRequestDto {
    private String comment;
    private String email;
    private String parentReplyId;

    public ReplyDto toDto() {
        return ReplyDto.builder()
                .comment(this.comment)
                .email(this.email)
                .parentReplyId(this.parentReplyId)
                .build();
    }
}
