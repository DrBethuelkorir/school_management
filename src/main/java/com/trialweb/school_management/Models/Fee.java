package com.trialweb.school_management.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalBillPerTerm;
    private BigDecimal totalPaidAmount;
    private BigDecimal balance;

    @ManyToOne()
    @JoinColumn(name = "fee")
    private Students students;

    public Fee(BigDecimal totalBillPerTerm, BigDecimal totalPaidAmount, BigDecimal balance, Students students) {
        this.totalBillPerTerm = totalBillPerTerm;
        this.totalPaidAmount = totalPaidAmount;
        this.balance = balance;
        this.students = students;
    }
}
