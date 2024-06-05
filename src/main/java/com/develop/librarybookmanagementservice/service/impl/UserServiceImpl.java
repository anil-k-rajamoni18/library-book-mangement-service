package com.develop.librarybookmanagementservice.service.impl;

import com.develop.librarybookmanagementservice.entity.UserInfo;
import com.develop.librarybookmanagementservice.repository.UserRepository;
import com.develop.librarybookmanagementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncode;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncode.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
        return String.format("User %s added successfully", userInfo.getUsername());
    }
}
