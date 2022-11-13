package com.artinus.community.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {
    @Column(name = "created_datetime")
    @CreatedDate
    private LocalDateTime createdDateTime;

    @Column(name = "modified_datetime")
    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @PrePersist
    public void onPrePersist() {
        this.createdDateTime = LocalDateTime.now().withNano(0);
        this.modifiedDateTime = this.createdDateTime;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.modifiedDateTime = LocalDateTime.now().withNano(0);
    }
}