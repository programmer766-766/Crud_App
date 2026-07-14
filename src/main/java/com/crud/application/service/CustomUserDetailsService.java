package com.crud.application.service;

import com.crud.application.entity.UserEntity;
import com.crud.application.exception.NoUserFoundException;
import com.crud.application.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                new NoUserFoundException("User not found with username:" + username));
        return new CustomUserDetails(userEntity);
    }
}
