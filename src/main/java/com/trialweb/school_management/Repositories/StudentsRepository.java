package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Models.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentsRepository extends JpaRepository<Students, Long> {

    Optional<Students> findByEmail(String email);
    @Query("SELECT s FROM Students s WHERE s.id = :userId")
    Optional<Students> findByUserId(@Param("userId") Long userId);

    Optional<Students> findByAdm(String adm);

    boolean existsByEmail(String email);

    boolean existsByAdm(String adm);


    long countByClassesId(Long classId);

    long countByStreamId(Long streamId);

    long countByDormId(Long dormId);

    List<Students> findByClassesId(Long classId);

    List<Students> findByStreamId(Long streamId);

    List<Students> findByDormId(Long dormId);

    List<Students> findByParentId(Long parentId);

}