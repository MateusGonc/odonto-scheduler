package com.br.odontoscheduler.service;

import com.br.odontoscheduler.model.User;
import com.br.odontoscheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User, UserRepository> implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserRepository getRepository() {
        return this.userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }
}
