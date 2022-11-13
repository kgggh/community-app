package com.artinus.community.domain;

import com.artinus.community.exception.PostCommonException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTime {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "post_id")
    private UUID id;

    private String title;
    private String description;

    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
    private List<Reply> replies = new ArrayList<>();

    private Post(String title, String description, String email) {
        validate(title, description);
        this.title = title;
        this.description = description;
        this.email = email;
    }

    public static Post newInstance(String title, String description, String email) {
        return new Post(title, description, email);
    }

    public void modify(String title, String description) {
        validate(title, description);
        this.title = title;
        this.description = description;
    }

    private void validate(String title, String description) {
        if( Strings.isBlank(title) ) {
            throw new PostCommonException("[INVALID] title is not null retry!");
        }
        if( Strings.isBlank(description) ) {
            throw new PostCommonException("[INVALID] description is not null retry!");
        }
    }
}
