package com.xmchx.vote.repository;

import com.xmchx.vote.model.Role;
import com.xmchx.vote.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/7 20:23
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName roleName);
}
