package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.ParentDto;
import com.trialweb.school_management.Responses.ParentsResponse;
import com.trialweb.school_management.Service.Parents.IParentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("parents")
public class ParentController {

    private final IParentsService parentsService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ParentsResponse> registerParent(@RequestBody ParentDto parentDto) {
        ParentsResponse response = parentsService.registerParent(parentDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ParentsResponse> getAllParents() {
        ParentsResponse response = parentsService.getAllParents();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ParentsResponse> getParentById(@PathVariable Long id) {
        ParentsResponse response = parentsService.getParentById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ParentsResponse> updateParent(@RequestBody ParentDto parentDto) {
        ParentsResponse response = parentsService.updateParent(parentDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ParentsResponse> deleteParent(@PathVariable Long id) {
        ParentsResponse response = parentsService.deleteParent(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}