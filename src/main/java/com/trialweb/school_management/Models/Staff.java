package com.trialweb.school_management.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Staff extends User {

    private String phone;
    private String staffID;

    public Staff(String phone, String staffID) {
        this.phone = phone;
        this.staffID = staffID;
    }
}
