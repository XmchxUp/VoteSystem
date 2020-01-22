package com.xmchx.vote.repository;

import com.xmchx.vote.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/15 20:22
 */
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Choice c WHERE c.poll.id = ?1")
    void deleteChoicesByPollId(Long id);
}
