package com.atdxt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserEncryptRepository extends JpaRepository<UserEntity3, Integer> {

}