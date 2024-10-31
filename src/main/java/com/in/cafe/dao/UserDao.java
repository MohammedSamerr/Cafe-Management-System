package com.in.cafe.dao;

import com.in.cafe.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDao extends JpaRepository<User,Integer> {

    User findByEmailId(@Param("email") String email);
}
