package com.app.service;

import com.app.helpers.AuthorityType;
import com.app.model.Authority;
import com.app.model.User;
import com.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository daoUser;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    /*@Lazy bo spring nie wiem który bean stworzyć jako pierwszy
    */
    @Autowired
    public UserServiceImpl(UserRepository daoUser, PasswordEncoder passwordEncoder, AuthorityService authorityService) {
        this.daoUser=daoUser;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;

        initializeDataInTables();
    }

    private void initializeDataInTables(){
        if (authorityService.findByType(AuthorityType.ROLE_ADMIN) == null && authorityService.findByType(AuthorityType.ROLE_USER) == null){
            authorityService.saveAuthority(authorityService.newAuthority(AuthorityType.ROLE_USER));
            authorityService.saveAuthority(authorityService.newAuthority(AuthorityType.ROLE_ADMIN));
        }

        if (findByEmail("admin@mail.com") == null){
            User user = newUser();
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setUniqueName("admin");
            user.setEmail("admin@mail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setAuthorities(Arrays.asList(authorityService.newAuthority(AuthorityType.ROLE_ADMIN)));
            daoUser.save(user);
        }
        if (findByEmail("johndoe_199x@mail.com") == null){
            User user = newUser();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setUniqueName("johndoe");
            user.setEmail("johndoe_199x@mail.com");
            user.setPassword(passwordEncoder.encode("j"));
            user.setAuthorities(Arrays.asList(authorityService.newAuthority(AuthorityType.ROLE_USER)));
            daoUser.save(user);
        }

    }

    public User findById(final int id){
        Optional<User> tmp = daoUser.findById(id);
        return tmp.orElse(null);
    }

    @Override
    public User findByEmail(final String email){
        return daoUser.findByEmail(email).orElse(null);
    }


    public User findByUniqueName(final String uniqueName){
        return daoUser.findByUniqueName(uniqueName).orElse(null);
    }

    @Transactional
    @Override
    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Arrays.asList(authorityService.newAuthority(AuthorityType.ROLE_USER)));
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email); //bo logujemy się za pomocą email
        if (user == null){
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Authority> authorities){
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
