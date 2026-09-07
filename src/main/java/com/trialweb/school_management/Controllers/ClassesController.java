package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.ClassesDto;
import com.trialweb.school_management.Responses.ClassResponses;
import com.trialweb.school_management.Service.Classes.ClassesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("classes")
public class ClassesController {

    private final ClassesService classesService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClassResponses> createClass(@RequestBody ClassesDto classesDto) {
        ClassResponses response = classesService.createClass(classesDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClassResponses> getAllClasses() {
        ClassResponses response = classesService.getAllClasses();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClassResponses> getClassById(@PathVariable Long id) {
        ClassResponses response = classesService.getClassById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClassResponses> updateClass(
            @PathVariable Long id,
            @RequestBody ClassesDto classesDto) {
        ClassResponses response = classesService.updateClass(id, classesDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClassResponses> deleteClass(@PathVariable Long id) {
        ClassResponses response = classesService.deleteClass(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}