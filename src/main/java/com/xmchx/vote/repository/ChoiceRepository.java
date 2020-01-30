package com.xmchx.vote.repository;

import com.xmchx.vote.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/15 20:22
 */
@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Choice c WHERE c.poll.id = ?1")
    void deleteChoicesByPollId(Long id);

    @Query("select c from Choice c where c.poll.id in ?1")
    List<Choice> findChoicesByPollIdIn(Long pollId);
}
