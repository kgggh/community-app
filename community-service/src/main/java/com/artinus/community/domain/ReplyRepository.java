package com.artinus.community.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReplyRepository extends JpaRepository<Reply, UUID>, ReplyCustomRepository {
}
