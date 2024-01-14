package com.booking.smartx.services.impl;

import com.booking.smartx.dao.UserRepository;
import com.booking.smartx.entities.User;
import com.booking.smartx.security.JwtUserFactory;
import com.booking.smartx.security.SecurityUtil;
import com.booking.smartx.services.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Primary
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private final SecurityUtil tokenUtil;

    public UserServiceImpl(UserRepository userRepository, SecurityUtil tokenUtil) {
        this.userRepository = userRepository;
        this.tokenUtil = tokenUtil;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(username);
//        if(Objects.isNull(user)){
//            throw new UsernameNotFoundException("User not found");
//        }
//        UserDetails userDetails = JwtUserFactory.create(user);
//        return userDetails;
//    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(username);
                if (Objects.isNull(user)) {
                    throw new UsernameNotFoundException("User not found");
                }
                UserDetails userDetails = JwtUserFactory.create(user);
                return userDetails;
            }
        };
    }
}
