package com.trialweb.school_management.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeeDto {
    private Long id;
    private BigDecimal totalBillPerTerm;
    private BigDecimal totalPaidAmount;
    private BigDecimal balance;
    private String adm;
    private StudentDto student;
}