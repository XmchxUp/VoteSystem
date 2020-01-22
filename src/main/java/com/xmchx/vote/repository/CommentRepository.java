package com.xmchx.vote.repository;

import com.xmchx.vote.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/13 19:02
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
