package com.trialweb.school_management.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
@DiscriminatorValue("parent")
public class Parent extends User {

    private String phoneNumber;
    private String address;
    private String relationshipWithStudent;

    @OneToMany(mappedBy = "parent")
    private List<Students> student;

    public Parent(String phoneNumber, String address,
                  String relationshipWithStudent, List<Students> student) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.relationshipWithStudent = relationshipWithStudent;
        this.student = student;
    }
}
