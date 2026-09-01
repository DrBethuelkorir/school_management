package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Models.Classes;
import com.trialweb.school_management.Models.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectsRepository extends JpaRepository<Subjects, Long> {
    Optional<Subjects> findBySubjectName(String name);
    boolean existsBySubjectName(String name);
    Long countBySubjectCode(Long subjectCode);
    Long countById(Long subjectId);
}
