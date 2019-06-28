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

    public Authority findByType(AuthorityType type){
        return daoAuthority.findByName(type).orElse(null);
    }

    private void saveAuthority(Authority authority){
        daoAuthority.save(authority);
    }

    public void deleteAuthority(AuthorityType type){
        daoAuthority.findByName(type).ifPresent(daoAuthority::delete);
    }

    public Authority createOrGetAuthority(AuthorityType type){
        Authority authority = findByType(type);
        if (authority == null){
            authority = new Authority(type);
            saveAuthority(authority);
        }
        return authority;
    }
}
