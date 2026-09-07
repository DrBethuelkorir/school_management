package com.trialweb.school_management.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@DiscriminatorValue("staff")
public class Staff extends User {

    private String staffId;
    private String position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Departments department;

    public Staff(String staffId, Departments department) {
        this.staffId = staffId;
        this.department = department;
    }
}