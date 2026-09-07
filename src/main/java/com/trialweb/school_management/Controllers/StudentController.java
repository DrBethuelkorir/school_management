package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.StudentDto;
import com.trialweb.school_management.Responses.StudentResponse;
import com.trialweb.school_management.Service.Studnets.IStudentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("students")
public class StudentController {

    private final IStudentsService studentService;

    @PostMapping("/register")
//    @PreAuthorize("hasAuthority('ADMIN')"
    public ResponseEntity<StudentResponse> registerStudent(@RequestBody StudentDto studentDto) {
        StudentResponse response = studentService.registerStudent(studentDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StudentResponse> getAllStudents() {
        StudentResponse response = studentService.getAllStudents();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        StudentResponse response = studentService.getStudentById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StudentResponse> updateStudent(@RequestBody StudentDto studentDto) {
        StudentResponse response = studentService.updateStudent(studentDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StudentResponse> deleteStudent(@PathVariable Long id) {
        StudentResponse response = studentService.deleteStudent(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}