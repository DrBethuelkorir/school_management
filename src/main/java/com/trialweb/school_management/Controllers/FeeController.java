package com.trialweb.school_management.Controllers;

import com.trialweb.school_management.Dtos.FeeDto;
import com.trialweb.school_management.Responses.FeeResponse;
import com.trialweb.school_management.Service.Fee.IFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("fees")
public class FeeController {

    private final IFeeService feeService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeeResponse> addFee(@RequestBody FeeDto feeDto) {
        FeeResponse response = feeService.addFee(feeDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeeResponse> getAllFees() {
        FeeResponse response = feeService.getAllFees();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeeResponse> getFeeById(@PathVariable Long id) {
        FeeResponse response = feeService.getFeeById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeeResponse> updateFee(@RequestBody FeeDto feeDto) {
        FeeResponse response = feeService.updateFee(feeDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeeResponse> deleteFee(@PathVariable Long id) {
        FeeResponse response = feeService.deleteFee(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/student/{adm}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeeResponse> getFeesByStudentAdm(@PathVariable String adm) {
        FeeResponse response = feeService.getFeesByStudentAdm(adm);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/student/{adm}/fee/{feeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeeResponse> getFeeByStudentAdmAndFeeId(
            @PathVariable String adm,
            @PathVariable Long feeId) {
        FeeResponse response = feeService.getFeeByStudentAdmAndFeeId(adm, feeId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{id}/payment")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeeResponse> updateFeePayment(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        FeeResponse response = feeService.updateFeePayment(id, amount);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}/with-student")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeeResponse> getFeeWithStudent(@PathVariable Long id) {
        FeeResponse response = feeService.getFeeWithStudent(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/student/{adm}/summary")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeeResponse> getStudentFeeSummary(@PathVariable String adm) {
        FeeResponse response = feeService.getStudentFeeSummary(adm);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}