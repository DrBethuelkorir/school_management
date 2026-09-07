package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.RolesDto;
import com.trialweb.school_management.Responses.RolesResponse;
import com.trialweb.school_management.Service.Roles.IRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
public class RolesController {

    private final IRoles rolesService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RolesResponse> createRole(@RequestBody RolesDto rolesDto) {
        RolesResponse response = rolesService.createRole(rolesDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RolesResponse> getAllRoles() {
        RolesResponse response = rolesService.getAllRoles();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RolesResponse> getRoleById(@PathVariable Long id) {
        RolesResponse response = rolesService.getRoleById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RolesResponse> updateRole(@RequestBody RolesDto rolesDto) {
        RolesResponse response = rolesService.updateRole(rolesDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RolesResponse> deleteRole(@PathVariable Long id) {
        RolesResponse response = rolesService.deleteRole(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}