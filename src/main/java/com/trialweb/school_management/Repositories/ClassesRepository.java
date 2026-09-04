package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Dtos.ClassesDto;
import com.trialweb.school_management.Models.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassesRepository extends JpaRepository<Classes, Long> {
    Optional<Classes> findByName(String name);
    boolean existsByName(String name);
    List<ClassesDto> findById(long classId);
}
