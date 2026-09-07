package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Models.Fee;
import com.trialweb.school_management.Models.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findByStudents(Students student);

    @Query("SELECT f FROM Fee f WHERE f.students.adm = :adm")
    List<Fee> findByStudentAdm(@Param("adm") String adm);

    @Query("SELECT f FROM Fee f WHERE f.students.adm = :adm AND f.id = :feeId")
    Optional<Fee> findByStudentAdmAndId(@Param("adm") String adm, @Param("feeId") Long feeId);

    @Query("SELECT f FROM Fee f WHERE f.students.adm = :adm ORDER BY f.id DESC")
    List<Fee> findLatestFeeByStudentAdm(@Param("adm") String adm);
}