package com.xmchx.vote.repository;

import com.xmchx.vote.model.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/13 18:34
 */
@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    Page<Poll> findByUserId(Long userId, Pageable pageable);

    long countByUserId(Long userId);

    List<Poll> findByIdIn(List<Long> pollIds);

    List<Poll> findByIdIn(List<Long> pollIds, Sort sort);

    @Query("select p from Poll p where p.title like ?1 or p.summary like ?1")
    Page<Poll> findByTitleLikeOrSummaryLike(String name, Pageable pageable);

    @Transactional
    void deletePollsByIdIn(List<Long> ids);
}
