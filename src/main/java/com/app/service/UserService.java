package com.app.service;

import com.app.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(final String email);
    void save(User user);
    void update(User user);
}
