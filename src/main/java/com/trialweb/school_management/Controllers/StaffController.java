package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.StaffDto;
import com.trialweb.school_management.Responses.StaffResponse;
import com.trialweb.school_management.Service.Staff.IStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("staff")
public class StaffController {

    private final IStaffService staffService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StaffResponse> registerStaff(@RequestBody StaffDto staffDto) {
        StaffResponse response = staffService.registerStaff(staffDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StaffResponse> getAllStaff() {
        StaffResponse response = staffService.getAllStaff();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StaffResponse> getStaffById(@PathVariable Long id) {
        StaffResponse response = staffService.getStaffById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StaffResponse> updateStaff(@RequestBody StaffDto staffDto) {
        StaffResponse response = staffService.updateStaff(staffDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StaffResponse> deleteStaff(@PathVariable Long id) {
        StaffResponse response = staffService.deleteStaff(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}