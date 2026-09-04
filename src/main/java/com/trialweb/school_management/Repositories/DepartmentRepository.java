package com.trialweb.school_management.Repositories;

import com.trialweb.school_management.Dtos.DepartmentDto;
import com.trialweb.school_management.Models.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Departments, Long> {
    Optional<Departments> findByDepartmentName(String name);
    boolean existsByDepartmentName(String name);

    Optional<Departments> findByDepartmentName(DepartmentDto department);

    @Query("SELECT d FROM Departments d WHERE LOWER(d.departmentName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(d.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Departments> searchDepartments(@Param("searchTerm") String searchTerm);
}
