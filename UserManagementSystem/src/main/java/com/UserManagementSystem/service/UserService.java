package com.UserManagementSystem.service;


import com.UserManagementSystem.entity.User;
import com.UserManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

//    public User saveUser(User user){
//        return userRepository.save(user);
//    }
    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();

    }
}
