package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Models.Classes;
import com.trialweb.school_management.Models.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Departments, Long> {
    Optional<Departments> findByDepartmentName(String name);
    boolean existsByDepartmentName(String name);
}
