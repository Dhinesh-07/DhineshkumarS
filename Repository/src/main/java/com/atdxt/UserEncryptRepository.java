package com.atdxt;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserEncryptRepository extends JpaRepository<UserEncrypt, Integer> {

    boolean existsByUserName(String username);
    Optional<UserEncrypt> findByUsername(String username);
}