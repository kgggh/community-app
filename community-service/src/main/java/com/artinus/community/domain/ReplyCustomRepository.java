package com.artinus.community.domain;

import com.artinus.community.service.model.ReplySearchCriteria;
import org.springframework.data.domain.Page;

public interface ReplyCustomRepository {
    Page<Reply> findByPostAndComment(ReplySearchCriteria replySearchCriteria);
}
