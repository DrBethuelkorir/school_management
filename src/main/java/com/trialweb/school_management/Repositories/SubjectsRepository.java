package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Models.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectsRepository extends JpaRepository<Subjects, Long> {
    Optional<Subjects> findBySubjectCode(String subjectCode);
    boolean existsBySubjectCode(String subjectCode);
}