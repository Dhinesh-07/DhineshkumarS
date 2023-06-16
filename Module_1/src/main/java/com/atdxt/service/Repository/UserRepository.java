package com.atdxt.service.Repository;

import com.atdxt.service.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

}
