package com.xmchx.vote.repository;

import com.xmchx.vote.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/13 19:04
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    @Transactional
    void deleteCategoriesByIdIn(List<Long> ids);
}
