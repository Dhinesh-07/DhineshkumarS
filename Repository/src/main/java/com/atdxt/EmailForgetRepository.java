package com.atdxt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailForgetRepository extends JpaRepository<EmailForget, Integer> {
    EmailForget findByToken(String token);
    EmailForget findByUser(UserEntity user);

}
