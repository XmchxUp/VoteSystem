package com.xmchx.vote.repository;

import com.xmchx.vote.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/7 17:12
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


    @Query(value = "select u from User u where u.username like ?1 or u.name like ?1 or u.email " +
            "like ?1")
    Page<User> findUsersByUsernameLikeOrNameLikeOrEmailLike(String name, Pageable pageable);

    @Transactional
    void deleteUsersByIdIn(List<Long> ids);

}
