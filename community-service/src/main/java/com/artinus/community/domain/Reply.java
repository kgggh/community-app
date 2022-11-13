package com.artinus.community.domain;

import com.artinus.community.exception.ReplyCommonException;
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
public class Reply extends BaseTime {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "reply_id")
    private UUID id;

    private String comment;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Reply parentReply;

    @OrderBy(value = "created_datetime DESC")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentReply", orphanRemoval = true)
    private List<Reply> childReplies = new ArrayList<>();

    private Reply(String comment, String email, Post post) {
        validate(comment, email);
        this.comment = comment;
        this.email = email;
        this.post = post;
    }

    public static Reply newInstance(String comment, String email, Post post) {
        return new Reply(comment, email, post);
    }

    public void addReply(Reply parentReply) {
        this.parentReply = parentReply;
        this.childReplies.add(this);
    }

    private void validate(String comment, String email) {
        if( Strings.isBlank(comment) ) {
            throw new ReplyCommonException("[INVALID] comment is not null retry!");
        }
        if( Strings.isBlank(email) ) {
            throw new ReplyCommonException("[INVALID] email is not null retry!");
        }
    }
}
