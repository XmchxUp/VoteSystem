package com.xmchx.vote.repository;

import com.xmchx.vote.model.ChoiceVoteCount;
import com.xmchx.vote.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/13 18:47
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("select new com.xmchx.vote.model.ChoiceVoteCount(v.choice.id,count(v.id)) from Vote v " +
            "where v.poll.id in :pollIds group by v.choice.id")
    List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds") List<Long> pollIds);

    @Query("select v from Vote v where v.user.id = :userId and v.poll.id in :pollIds")
    List<Vote> findByUserIdAndPollIdIn(@Param("userId") Long userId,
                                       @Param("pollIds") List<Long> pollIds);

    @Query("select v from Vote v where v.user.id = :userId and v.poll.id = :pollId")
    Vote findByUserIdAndPollId(@Param("userId") Long userId, @Param("pollId") Long pollId);

    @Query("select count(v.id) from Vote v where v.user.id = :userId and v.poll.id = :pollId")
    long countByUserIdAndPollId(@Param("userId") Long userId, @Param("pollId") Long pollId);

    @Query("select count(distinct v.poll.id) from Vote v where v.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("select count(v.choice) from Vote v where v.choice.id = :choiceId")
    long countByChoiceId(@Param("choiceId")Long choiceId);

    @Query("select v.poll.id from Vote v where v.user.id = :userId")
    Page<Long> findVotedPollIdsByUserId(@Param("userId") Long userId, Pageable pageable);

}
