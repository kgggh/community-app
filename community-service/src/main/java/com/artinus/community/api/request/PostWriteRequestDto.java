package com.artinus.community.api.request;

import com.artinus.community.service.model.PostDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostWriteRequestDto {
    private String title;
    private String description;
    private String email;

    public PostDto toDto() {
        return PostDto.builder()
                .title(this.title)
                .description(this.description)
                .email(this.email)
                .build();
    }
}
