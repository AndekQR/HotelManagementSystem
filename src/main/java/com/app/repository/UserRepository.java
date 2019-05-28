package com.app.repository;

import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    /*Optional zabezpiecza przed nullem
    * w innych klasach sprawdzamy czy nie jest nullem przez nazwa.isPresent()
    * jezeli zwraca true to nazwa.get() pobieramy zawartość */

    Optional<User> findByEmail(String email);
    Optional<User> findByUniqueName(String uniqueName);
    void deleteByEmail(String email);
    void deleteByUniqueName(String uniqueName);

}
