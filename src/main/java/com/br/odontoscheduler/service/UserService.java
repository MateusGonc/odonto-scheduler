package com.br.odontoscheduler.service;

import com.br.odontoscheduler.model.User;
import com.br.odontoscheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public User updateUser(String id, User user) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            currentUser.setUpdatedAt(LocalDateTime.now());
            currentUser.setUsername(user.getUsername());
            currentUser.setPassword(user.getPassword());
            currentUser.setFullName(user.getFullName());

            return this.userRepository.save(currentUser);
        }

        return null;
    }
}
