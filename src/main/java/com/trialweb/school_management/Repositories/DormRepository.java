package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Models.Classes;
import com.trialweb.school_management.Models.Dorm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DormRepository extends JpaRepository<Dorm, Long> {
    Optional<Dorm> findByName(String name);
    boolean existsByName(String name);
}
