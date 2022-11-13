package com.artinus.community.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@ToString
@Getter
public class PostDto {
    private String postId;
    private String title;
    private String description;
    private String email;
    private List<ReplyDto> replies;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
}
