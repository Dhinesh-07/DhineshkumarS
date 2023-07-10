package com.atdxt;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);


}
