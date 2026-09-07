package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.TeacherDto;
import com.trialweb.school_management.Responses.TeacherResponse;
import com.trialweb.school_management.Service.Teacher.ITeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("teachers")
public class TeacherController {

    private final ITeacherService teacherService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TeacherResponse> registerTeacher(@RequestBody TeacherDto teacherDto) {
        TeacherResponse response = teacherService.registerTeacher(teacherDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TeacherResponse> getAllTeachers() {
        TeacherResponse response = teacherService.getAllTeachers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable Long id) {
        TeacherResponse response = teacherService.getTeacherById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TeacherResponse> updateTeacher(@RequestBody TeacherDto teacherDto) {
        TeacherResponse response = teacherService.updateTeacher(teacherDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TeacherResponse> deleteTeacher(@PathVariable Long id) {
        TeacherResponse response = teacherService.deleteTeacher(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}