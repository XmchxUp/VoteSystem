package com.xmchx.vote.repository;

import com.xmchx.vote.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/13 19:02
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByName(String name);

    @Transactional
    void deleteTagsByIdIn(List<Long> ids);

    List<Tag> findTagsByNameIn(List<String> names);
}
