package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.StreamDto;
import com.trialweb.school_management.Responses.StreamResponse;
import com.trialweb.school_management.Service.Stream.IStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("streams")
public class StreamController {

    private final IStreamService streamService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StreamResponse> createStream(@RequestBody StreamDto streamDto) {
        StreamResponse response = streamService.createStream(streamDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StreamResponse> getAllStreams() {
        StreamResponse response = streamService.getAllStreams();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StreamResponse> getStreamById(@PathVariable Long id) {
        StreamResponse response = streamService.getStreamById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StreamResponse> getStreamsByClass(@PathVariable Long classId) {
        StreamResponse response = streamService.getStreamsByClass(classId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StreamResponse> updateStream(
            @PathVariable Long id,
            @RequestBody StreamDto streamDto) {
        StreamResponse response = streamService.updateStream(id, streamDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StreamResponse> deleteStream(@PathVariable Long id) {
        StreamResponse response = streamService.deleteStream(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{streamId}/add-student/{studentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StreamResponse> addStudentToStream(
            @PathVariable Long streamId,
            @PathVariable Long studentId) {
        StreamResponse response = streamService.addStudentToStream(streamId, studentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{streamId}/remove-student/{studentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StreamResponse> removeStudentFromStream(
            @PathVariable Long streamId,
            @PathVariable Long studentId) {
        StreamResponse response = streamService.removeStudentFromStream(streamId, studentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}