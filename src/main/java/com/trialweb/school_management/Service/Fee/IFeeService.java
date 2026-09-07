package com.trialweb.school_management.Service.Fee;

import com.trialweb.school_management.Dtos.FeeDto;
import com.trialweb.school_management.Responses.FeeResponse;

import java.math.BigDecimal;

public interface IFeeService {
    // Basic CRUD Operations
    FeeResponse getAllFees();
    FeeResponse getFeeById(Long id);
    FeeResponse addFee(FeeDto feeDto);
    FeeResponse updateFee(FeeDto feeDto);
    FeeResponse deleteFee(Long id);

    // Additional Methods using ADM
    FeeResponse getFeesByStudentAdm(String adm);
    FeeResponse getFeeByStudentAdmAndFeeId(String adm, Long feeId);
    FeeResponse updateFeePayment(Long feeId, BigDecimal amountPaid);
    FeeResponse getFeeWithStudent(Long id);
    FeeResponse getStudentFeeSummary(String adm);  // New method for fee summary
}