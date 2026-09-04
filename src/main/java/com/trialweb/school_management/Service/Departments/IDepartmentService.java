package com.trialweb.school_management.Service.Departments;

import com.trialweb.school_management.Dtos.DepartmentDto;
import com.trialweb.school_management.Responses.DepartmentResponse;

public interface IDepartmentService {

    // ==================== CREATE ====================
    DepartmentResponse createDepartment(DepartmentDto departmentDto);

    // ==================== READ ====================
    DepartmentResponse getDepartmentById(Long id);
    DepartmentResponse getAllDepartments();
    DepartmentResponse searchDepartments(String searchTerm);

    // ==================== UPDATE ====================
    DepartmentResponse updateDepartment(Long id, DepartmentDto departmentDto);

    // ==================== DELETE ====================
    DepartmentResponse deleteDepartment(Long id);

    // ==================== ASSIGNMENT METHODS ====================
    DepartmentResponse addTeacherToDepartment(Long departmentId, Long teacherId);
    DepartmentResponse removeTeacherFromDepartment(Long departmentId, Long teacherId);

    DepartmentResponse addSubjectToDepartment(Long departmentId, Long subjectId);
    DepartmentResponse removeSubjectFromDepartment(Long departmentId, Long subjectId);

    DepartmentResponse addStaffToDepartment(Long departmentId, Long staffId);
    DepartmentResponse removeStaffFromDepartment(Long departmentId, Long staffId);

    // ==================== DETAILED VIEWS ====================
    DepartmentResponse getTeachersInDepartment(Long departmentId);
    DepartmentResponse getSubjectsInDepartment(Long departmentId);
    DepartmentResponse getStaffInDepartment(Long departmentId);
    DepartmentResponse getDepartmentStatistics(Long departmentId);
}