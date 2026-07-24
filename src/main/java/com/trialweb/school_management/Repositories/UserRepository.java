package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Models.Classes;
import com.trialweb.school_management.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    boolean existsByEmail(String email);
}
