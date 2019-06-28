package com.app;

import com.app.helpers.AuthorityType;
import com.app.model.Authority;
import com.app.service.AuthorityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorityServiceTests {

    @Autowired
    private AuthorityService authorityService;

    @Test
    public void shouldReturnCorrectRoles() {
        Authority authority=authorityService.findByType(AuthorityType.ROLE_USER);
        assertThat(authority.getName()).isEqualTo(AuthorityType.ROLE_USER);
        authority = authorityService.findByType(AuthorityType.ROLE_ADMIN);
        assertThat(authority.getName()).isEqualTo(AuthorityType.ROLE_ADMIN);
    }

    @Test
    @Rollback
    public void shouldDeleteAuthority(){
        authorityService.deleteAuthority(AuthorityType.ROLE_USER);
        assertThat(authorityService.findByType(AuthorityType.ROLE_USER)).isNull();
        authorityService.createOrGetAuthority(AuthorityType.ROLE_USER);
    }

    @Test
    public void shouldCreateAnAuthority(){
        authorityService.createOrGetAuthority(AuthorityType.ROLE_USER);
        assertThat(authorityService.findByType(AuthorityType.ROLE_USER)).isNotNull();
        authorityService.createOrGetAuthority(AuthorityType.ROLE_ADMIN);
        assertThat(authorityService.findByType(AuthorityType.ROLE_ADMIN)).isNotNull();
    }


}
