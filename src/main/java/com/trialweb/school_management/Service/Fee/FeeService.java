package com.trialweb.school_management.Service.Fee;

import com.trialweb.school_management.Dtos.FeeDto;
import com.trialweb.school_management.Models.Fee;
import com.trialweb.school_management.Models.Students;
import com.trialweb.school_management.Repositories.FeeRepository;
import com.trialweb.school_management.Repositories.StudentsRepository;
import com.trialweb.school_management.Responses.FeeResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FeeService implements IFeeService {

    private final FeeRepository feeRepository;
    private final StudentsRepository studentRepository;

    @Override
    public FeeResponse getAllFees() {
        FeeResponse response = new FeeResponse();
        try {
            List<Fee> fees = feeRepository.findAll();

            List<FeeDto> feeDtoList = fees.stream()
                    .map(Utils::mapFeeEntityToFeeDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Fees retrieved successfully");
            response.setFeeDtoList(feeDtoList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving fees: " + e.getMessage());
        }
        return response;
    }

    @Override
    public FeeResponse getFeeById(Long id) {
        FeeResponse response = new FeeResponse();
        try {
            Fee fee = feeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Fee not found with id: " + id));

            FeeDto feeDto = Utils.mapFeeEntityToFeeDto(fee);

            response.setStatusCode(200);
            response.setMessage("Fee retrieved successfully");
            response.setFeeDto(feeDto);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving fee: " + e.getMessage());
        }
        return response;
    }

    @Override
    public FeeResponse addFee(FeeDto feeDto) {
        FeeResponse response = new FeeResponse();
        try {
            // Validate student exists by ADM
            if (feeDto.getAdm() == null || feeDto.getAdm().isEmpty()) {
                response.setStatusCode(400);
                response.setMessage("Student Admission Number (ADM) is required");
                return response;
            }

            Students student = studentRepository.findByAdm(feeDto.getAdm())
                    .orElseThrow(() -> new RuntimeException("Student not found with ADM: " + feeDto.getAdm()));

            // Calculate balance
            BigDecimal balance = feeDto.getTotalBillPerTerm().subtract(feeDto.getTotalPaidAmount());

            // Create new fee
            Fee fee = new Fee();
            fee.setTotalBillPerTerm(feeDto.getTotalBillPerTerm());
            fee.setTotalPaidAmount(feeDto.getTotalPaidAmount());
            fee.setBalance(balance);
            fee.setStudents(student);

            Fee savedFee = feeRepository.save(fee);
            FeeDto savedFeeDto = Utils.mapFeeEntityToFeeDto(savedFee);

            response.setStatusCode(201);
            response.setMessage("Fee added successfully for student with ADM: " + feeDto.getAdm());
            response.setFeeDto(savedFeeDto);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding fee: " + e.getMessage());
        }
        return response;
    }

    @Override
    public FeeResponse updateFee(FeeDto feeDto) {
        FeeResponse response = new FeeResponse();
        try {
            if (feeDto.getId() == null) {
                response.setStatusCode(400);
                response.setMessage("Fee ID is required for update");
                return response;
            }

            Fee existingFee = feeRepository.findById(feeDto.getId())
                    .orElseThrow(() -> new RuntimeException("Fee not found with id: " + feeDto.getId()));

            // Update fields
            if (feeDto.getTotalBillPerTerm() != null) {
                existingFee.setTotalBillPerTerm(feeDto.getTotalBillPerTerm());
            }

            if (feeDto.getTotalPaidAmount() != null) {
                existingFee.setTotalPaidAmount(feeDto.getTotalPaidAmount());
            }

            // Recalculate balance
            BigDecimal balance = existingFee.getTotalBillPerTerm().subtract(existingFee.getTotalPaidAmount());
            existingFee.setBalance(balance);

            // Update student if ADM is provided
            if (feeDto.getAdm() != null && !feeDto.getAdm().isEmpty()) {
                Students student = studentRepository.findByAdm(feeDto.getAdm())
                        .orElseThrow(() -> new RuntimeException("Student not found with ADM: " + feeDto.getAdm()));
                existingFee.setStudents(student);
            }

            Fee updatedFee = feeRepository.save(existingFee);
            FeeDto updatedFeeDto = Utils.mapFeeEntityToFeeDto(updatedFee);

            response.setStatusCode(200);
            response.setMessage("Fee updated successfully");
            response.setFeeDto(updatedFeeDto);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating fee: " + e.getMessage());
        }
        return response;
    }

    @Override
    public FeeResponse deleteFee(Long id) {
        FeeResponse response = new FeeResponse();
        try {
            Fee fee = feeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Fee not found with id: " + id));

            feeRepository.delete(fee);

            response.setStatusCode(200);
            response.setMessage("Fee deleted successfully");
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting fee: " + e.getMessage());
        }
        return response;
    }

    // Additional Methods using ADM

    @Override
    public FeeResponse getFeesByStudentAdm(String adm) {
        FeeResponse response = new FeeResponse();
        try {
            // Verify student exists
            if (!studentRepository.existsByAdm(adm)) {
                response.setStatusCode(404);
                response.setMessage("Student not found with ADM: " + adm);
                return response;
            }

            List<Fee> fees = feeRepository.findByStudentAdm(adm);

            List<FeeDto> feeDtoList = fees.stream()
                    .map(Utils::mapFeeEntityToFeeDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Fees retrieved successfully for student with ADM: " + adm);
            response.setFeeDtoList(feeDtoList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving student fees: " + e.getMessage());
        }
        return response;
    }

    @Override
    public FeeResponse getFeeByStudentAdmAndFeeId(String adm, Long feeId) {
        FeeResponse response = new FeeResponse();
        try {
            // Verify student exists
            if (!studentRepository.existsByAdm(adm)) {
                response.setStatusCode(404);
                response.setMessage("Student not found with ADM: " + adm);
                return response;
            }

            Fee fee = feeRepository.findByStudentAdmAndId(adm, feeId)
                    .orElseThrow(() -> new RuntimeException("Fee not found for student with ADM: " + adm + " and fee ID: " + feeId));

            FeeDto feeDto = Utils.mapFeeEntityToFeeDto(fee);

            response.setStatusCode(200);
            response.setMessage("Fee retrieved successfully");
            response.setFeeDto(feeDto);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving fee: " + e.getMessage());
        }
        return response;
    }

    @Override
    public FeeResponse updateFeePayment(Long feeId, BigDecimal amountPaid) {
        FeeResponse response = new FeeResponse();
        try {
            if (amountPaid == null || amountPaid.compareTo(BigDecimal.ZERO) <= 0) {
                response.setStatusCode(400);
                response.setMessage("Payment amount must be greater than zero");
                return response;
            }

            Fee fee = feeRepository.findById(feeId)
                    .orElseThrow(() -> new RuntimeException("Fee not found with id: " + feeId));

            // Update paid amount
            BigDecimal newTotalPaid = fee.getTotalPaidAmount().add(amountPaid);

            // Check if payment exceeds total bill
            if (newTotalPaid.compareTo(fee.getTotalBillPerTerm()) > 0) {
                response.setStatusCode(400);
                response.setMessage("Payment exceeds total bill amount");
                return response;
            }

            fee.setTotalPaidAmount(newTotalPaid);
            BigDecimal newBalance = fee.getTotalBillPerTerm().subtract(newTotalPaid);
            fee.setBalance(newBalance);

            Fee updatedFee = feeRepository.save(fee);
            FeeDto updatedFeeDto = Utils.mapFeeEntityToFeeDto(updatedFee);

            response.setStatusCode(200);
            response.setMessage("Payment updated successfully for fee ID: " + feeId);
            response.setFeeDto(updatedFeeDto);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating payment: " + e.getMessage());
        }
        return response;
    }

    @Override
    public FeeResponse getFeeWithStudent(Long id) {
        FeeResponse response = new FeeResponse();
        try {
            Fee fee = feeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Fee not found with id: " + id));

            FeeDto feeDto = Utils.mapFeeEntityToFeeDtoPlusStudent(fee);

            response.setStatusCode(200);
            response.setMessage("Fee with student details retrieved successfully");
            response.setFeeDto(feeDto);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving fee with student: " + e.getMessage());
        }
        return response;
    }

    @Override
    public FeeResponse getStudentFeeSummary(String adm) {
        FeeResponse response = new FeeResponse();
        try {
            // Verify student exists
            Students student = studentRepository.findByAdm(adm)
                    .orElseThrow(() -> new RuntimeException("Student not found with ADM: " + adm));

            List<Fee> fees = feeRepository.findByStudentAdm(adm);

            if (fees.isEmpty()) {
                response.setStatusCode(200);
                response.setMessage("No fees found for student with ADM: " + adm);
                response.setFeeDtoList(List.of());
                return response;
            }

            // Calculate summary
            BigDecimal totalBill = fees.stream()
                    .map(Fee::getTotalBillPerTerm)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalPaid = fees.stream()
                    .map(Fee::getTotalPaidAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalBalance = fees.stream()
                    .map(Fee::getBalance)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            List<FeeDto> feeDtoList = fees.stream()
                    .map(Utils::mapFeeEntityToFeeDto)
                    .collect(Collectors.toList());

            // Add summary information to the response message
            String summary = String.format(
                    "Student: %s %s (ADM: %s) - Total Bill: %.2f, Total Paid: %.2f, Outstanding Balance: %.2f",
                    student.getFirstName(), student.getLastName(), adm, totalBill, totalPaid, totalBalance
            );

            response.setStatusCode(200);
            response.setMessage(summary);
            response.setFeeDtoList(feeDtoList);
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving fee summary: " + e.getMessage());
        }
        return response;
    }
}