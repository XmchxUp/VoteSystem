package com.xmchx.vote.repository;

import com.xmchx.vote.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/13 19:02
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Transactional
    void deleteCommentsByIdIn(List<Long> ids);

    @Transactional
    @Modifying
    @Query(value = "update comments set comment_status = 1 where id in ?1",nativeQuery = true)
    int updateCommentStatus(@Param("pollIds") List<Long> ids);
}
