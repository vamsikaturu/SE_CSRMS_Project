package com.pack.seproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.seproject.model.User;
import com.pack.seproject.repository.UserRespository;

@Service
public class UserService {
    
    @Autowired
	UserRespository userRespository;

    public User saveUser(User user) {
        return userRespository.save(user);
    }

    public User findByUsername(String username) {
        return userRespository.findByUsername(username);
    }


}
