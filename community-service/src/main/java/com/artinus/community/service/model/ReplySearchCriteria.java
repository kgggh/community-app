package com.artinus.community.service.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplySearchCriteria {
    private String postId;
    private String replyId;
    private int page;
    private final int size = 10;

    public ReplySearchCriteria(String postId, String replyId, Integer page) {
        this.postId = postId;
        this.replyId = replyId;
        this.page = page == null ? 0 : page;
    }
}
