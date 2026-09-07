package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.DepartmentDto;
import com.trialweb.school_management.Responses.DepartmentResponse;
import com.trialweb.school_management.Service.Departments.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("departments")
public class DepartmentController {

    private final IDepartmentService departmentService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentResponse response = departmentService.createDepartment(departmentDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> getAllDepartments() {
        DepartmentResponse response = departmentService.getAllDepartments();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {
        DepartmentResponse response = departmentService.getDepartmentById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> searchDepartments(@RequestParam String searchTerm) {
        DepartmentResponse response = departmentService.searchDepartments(searchTerm);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentDto departmentDto) {
        DepartmentResponse response = departmentService.updateDepartment(id, departmentDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> deleteDepartment(@PathVariable Long id) {
        DepartmentResponse response = departmentService.deleteDepartment(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{departmentId}/teacher/{teacherId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> addTeacherToDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long teacherId) {
        DepartmentResponse response = departmentService.addTeacherToDepartment(departmentId, teacherId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{departmentId}/teacher/{teacherId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> removeTeacherFromDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long teacherId) {
        DepartmentResponse response = departmentService.removeTeacherFromDepartment(departmentId, teacherId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{departmentId}/subject/{subjectId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> addSubjectToDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long subjectId) {
        DepartmentResponse response = departmentService.addSubjectToDepartment(departmentId, subjectId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{departmentId}/subject/{subjectId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> removeSubjectFromDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long subjectId) {
        DepartmentResponse response = departmentService.removeSubjectFromDepartment(departmentId, subjectId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{departmentId}/staff/{staffId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> addStaffToDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long staffId) {
        DepartmentResponse response = departmentService.addStaffToDepartment(departmentId, staffId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{departmentId}/staff/{staffId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> removeStaffFromDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long staffId) {
        DepartmentResponse response = departmentService.removeStaffFromDepartment(departmentId, staffId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{departmentId}/teachers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> getTeachersInDepartment(@PathVariable Long departmentId) {
        DepartmentResponse response = departmentService.getTeachersInDepartment(departmentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{departmentId}/subjects")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> getSubjectsInDepartment(@PathVariable Long departmentId) {
        DepartmentResponse response = departmentService.getSubjectsInDepartment(departmentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{departmentId}/staff")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> getStaffInDepartment(@PathVariable Long departmentId) {
        DepartmentResponse response = departmentService.getStaffInDepartment(departmentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{departmentId}/statistics")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentResponse> getDepartmentStatistics(@PathVariable Long departmentId) {
        DepartmentResponse response = departmentService.getDepartmentStatistics(departmentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}