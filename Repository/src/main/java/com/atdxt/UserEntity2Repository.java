package com.atdxt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntity2Repository extends JpaRepository<UserEntity2, Integer> {

}
