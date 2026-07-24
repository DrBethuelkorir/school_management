package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Models.Classes;
import com.trialweb.school_management.Models.Departments;
import com.trialweb.school_management.Models.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeachersRepository extends JpaRepository<Teachers, Long> {
    Optional<Teachers> findByName(String name);
    boolean existsByName(String name);
    Long countByDepartmentsId(Long id);
    Long countByTeacherID(long teacherId);

}
