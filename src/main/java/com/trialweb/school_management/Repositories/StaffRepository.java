package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Models.Classes;
import com.trialweb.school_management.Models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByName(String name);
    boolean existsByEmail(String email);
}
