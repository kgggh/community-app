package com.artinus.community.api.response;

import com.artinus.community.domain.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostResponseDto {
    private String postId;
    private String title;
    private String description;
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/seoul")
    private LocalDateTime createdDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/seoul")
    private LocalDateTime modifiedDateTime;

    public PostResponseDto(Post post) {
        this.postId = post.getId().toString();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.email = post.getEmail();
        this.createdDateTime = post.getCreatedDateTime();
        this.modifiedDateTime = post.getModifiedDateTime();
    }
}
