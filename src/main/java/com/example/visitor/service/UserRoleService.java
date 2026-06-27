package com.example.visitor.service;

import com.example.visitor.entity.UserRole;
import com.example.visitor.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository repository;

    public String register(UserRole user){

        if(repository.findByUsername(user.getUsername()).isPresent()){
            return "Username Already Exists";
        }

        repository.save(user);

        return "Registration Successful";
    }

    public String login(String username,String password){

        UserRole user = repository.findByUsername(username)
                .orElseThrow();

        if(!user.getPassword().equals(password)){
            return "Invalid Password";
        }

        return "Login Successful : " + user.getRole();
    }

}