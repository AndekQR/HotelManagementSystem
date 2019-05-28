package com.app.service;

import com.app.model.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService {

    private final UserRepository daoUser;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository daoUser, PasswordEncoder passwordEncoder) {
        this.daoUser=daoUser;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(final int id){
        Optional<User> tmp = daoUser.findById(id);
        return tmp.orElse(null);
    }

    public User findByEmail(final String email){
        return daoUser.findByEmail(email).orElse(null);
    }

    public User findByUniqueName(final String uniqueName){
        return daoUser.findByUniqueName(uniqueName).orElse(null);
    }

    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        daoUser.save(user);
    }

    public void deleteByUniqueName(final String uniqueName){
        daoUser.deleteByUniqueName(uniqueName);
    }

    public void deleteByEmail(final String email){
        daoUser.deleteByEmail(email);
    }

    public List<User> findAllUsers(){
        return daoUser.findAll();
    }

    public boolean isUniqueNameIsUnique(final String uniqueName){
        User user = findByUniqueName(uniqueName);
        return user == null;
    }

    public boolean isEmailUnique(final String email){
        User user = findByEmail(email);
        return user == null;
    }

    public User newUser(){
        return new User();
    }

}
