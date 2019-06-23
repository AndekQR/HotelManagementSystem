package com.app.service;

import com.app.helpers.AuthorityType;
import com.app.model.Authority;
import com.app.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {


    private final AuthorityRepository daoAuthority;

    public AuthorityService(AuthorityRepository daoAuthority) {
        this.daoAuthority=daoAuthority;
    }

    Authority findByType(AuthorityType type){
        return daoAuthority.findByName(type);
    }

    void saveAuthority(Authority authority){
        daoAuthority.save(authority);
    }

    public Authority createOrGetAuthority(AuthorityType type){
        Authority authority = findByType(type);
        if (authority == null)
            authority = new Authority(type);
        return authority;
    }
}
