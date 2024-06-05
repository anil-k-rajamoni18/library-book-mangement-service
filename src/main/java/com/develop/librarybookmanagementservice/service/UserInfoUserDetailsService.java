package com.develop.librarybookmanagementservice.service;

import com.develop.librarybookmanagementservice.config.UserInfoUserDetails;
import com.develop.librarybookmanagementservice.entity.UserInfo;
import com.develop.librarybookmanagementservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);
        //UserInfoUserDetails userInfoUserDetails = new UserInfoUserDetails(user.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));
        return userInfo.map(UserInfoUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
