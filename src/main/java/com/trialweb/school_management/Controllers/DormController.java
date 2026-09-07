package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.DormDto;
import com.trialweb.school_management.Responses.DormResponse;
import com.trialweb.school_management.Service.Dorm.IDormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("dorms")
public class DormController {

    private final IDormService dormService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DormResponse> createDorm(@RequestBody DormDto dormDto) {
        DormResponse response = dormService.createDorm(dormDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DormResponse> getAllDorms() {
        DormResponse response = dormService.getAllDorms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DormResponse> getDormById(@PathVariable Long id) {
        DormResponse response = dormService.getDormById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DormResponse> updateDorm(
            @PathVariable Long id,
            @RequestBody DormDto dormDto) {
        DormResponse response = dormService.updateDorm(id, dormDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DormResponse> deleteDorm(@PathVariable Long id) {
        DormResponse response = dormService.deleteDorm(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{dormId}/assign-student/{studentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DormResponse> assignStudentToDorm(
            @PathVariable Long studentId,
            @PathVariable Long dormId) {
        DormResponse response = dormService.assignStudentToDorm(studentId, dormId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/remove-student/{studentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DormResponse> removeStudentFromDorm(@PathVariable Long studentId) {
        DormResponse response = dormService.removeStudentFromDorm(studentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{studentId}/transfer-to/{newDormId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DormResponse> transferStudentToDorm(
            @PathVariable Long studentId,
            @PathVariable Long newDormId) {
        DormResponse response = dormService.transferStudentToDorm(studentId, newDormId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{dormId}/students")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DormResponse> getStudentsInDorm(@PathVariable Long dormId) {
        DormResponse response = dormService.getStudentsInDorm(dormId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}