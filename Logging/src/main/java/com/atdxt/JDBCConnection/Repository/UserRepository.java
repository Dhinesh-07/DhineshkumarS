package com.atdxt.JDBCConnection.Repository;

import com.atdxt.JDBCConnection.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

}
