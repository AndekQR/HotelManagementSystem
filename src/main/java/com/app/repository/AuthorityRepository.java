package com.app.repository;

import com.app.helpers.AuthorityType;
import com.app.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByName(AuthorityType type);
}
