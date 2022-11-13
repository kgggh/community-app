package com.artinus.community.domain;

import com.artinus.community.service.model.ReplySearchCriteria;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ReplyCustomRepositoryImpl implements ReplyCustomRepository {
    private final JPAQueryFactory query;
    private final QReply qReply = QReply.reply;

    @Override
    public Page<Reply> findByPostAndComment(ReplySearchCriteria replySearchCriteria) {
        List<Reply> result =
                query.selectFrom(qReply)
                        .leftJoin(qReply.childReplies)
                        .fetchJoin()
                        .where(
                                eqPost(replySearchCriteria.getPostId()),
                                eqReply(replySearchCriteria.getReplyId())
                        )
                        .orderBy(
                                qReply.parentReply.id.asc().nullsFirst(),
                                qReply.createdDateTime.desc())
                        .limit(replySearchCriteria.getSize())
                        .fetch();

        JPAQuery<Reply> countQuery = query.selectFrom(qReply)
                .where(
                        eqPost(replySearchCriteria.getPostId()),
                        eqReply(replySearchCriteria.getReplyId())
                );

        return PageableExecutionUtils.getPage(result, makePageRequest(replySearchCriteria), () -> countQuery.fetch().size());
    }

    private BooleanExpression eqPost(String postId) {
        if( !Strings.isBlank(postId) ) {
            return qReply.post.id.eq(UUID.fromString(postId));
        }
        return null;
    }

    private BooleanExpression eqReply(String replyId) {
        if( !Strings.isBlank(replyId) ) {
            return qReply.id.eq(UUID.fromString(replyId));
        }
        return null;
    }

    private PageRequest makePageRequest(ReplySearchCriteria replySearchCriteria) {
        return PageRequest.of(replySearchCriteria.getPage(), replySearchCriteria.getSize());
    }
}
