package com.pack.seproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pack.seproject.model.User;

@Repository
public interface UserRespository extends JpaRepository<User, Integer> {

    User findByUsername(String username );
    
}
