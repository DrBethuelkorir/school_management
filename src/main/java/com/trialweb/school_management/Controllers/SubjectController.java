package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.SubjectDto;
import com.trialweb.school_management.Responses.SubjectResponse;
import com.trialweb.school_management.Service.Subjects.ISubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("subjects")
public class SubjectController {

    private final ISubjectService subjectService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectResponse> addSubject(@RequestBody SubjectDto subjectDto) {
        SubjectResponse response = subjectService.addSubject(subjectDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectResponse> getAllSubjects() {
        SubjectResponse response = subjectService.getAllSubjects();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable int id) {
        SubjectResponse response = subjectService.getSubjectById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/code/{subjectCode}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectResponse> getSubjectByCode(@PathVariable String subjectCode) {
        SubjectResponse response = subjectService.getSubjectByCode(subjectCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}/all-relationships")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectResponse> getSubjectWithAllRelationships(@PathVariable int id) {
        SubjectResponse response = subjectService.getSubjectWithAllRelationships(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectResponse> updateSubject(@RequestBody SubjectDto subjectDto) {
        SubjectResponse response = subjectService.updateSubject(subjectDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubjectResponse> deleteSubject(@PathVariable int id) {
        SubjectResponse response = subjectService.deleteSubject(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}