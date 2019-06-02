package com.app.repository;

import com.app.helpers.AuthorityType;
import com.app.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findByName(AuthorityType type);
}
