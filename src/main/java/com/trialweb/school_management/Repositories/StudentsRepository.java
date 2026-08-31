package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Models.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentsRepository extends JpaRepository<Students, Long> {

    Optional<Students> findByEmail(String email);

    Optional<Students> findByAdm(String adm);

    boolean existsByEmail(String email);

    boolean existsByAdm(String adm);


    long countByClassId(Long classId);

    long countByStreamId(Long streamId);

    long countByDormId(Long dormId);

    List<Students> findByClassesId(Long classId);

    List<Students> findByStreamId(Long streamId);

    List<Students> findByDormId(Long dormId);

    List<Students> findByParentId(Long parentId);

}