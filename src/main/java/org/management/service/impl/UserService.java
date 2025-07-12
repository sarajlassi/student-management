package org.management.service.impl;

import org.management.entity.UserApps;
import org.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password) {
        UserApps userApps = new UserApps();
        userApps.setUsername(username);
        userApps.setPassword(passwordEncoder.encode(password));
        userApps.setRole("USER"); // or "ADMIN" if needed
        userRepository.save(userApps);
    }

    public Optional<UserApps> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
