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
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String relationshipWithStudent;

    @OneToMany(mappedBy = "parent")
    private List<Students> student;


    public Parent(
            String firstName, String lastName, String email,
            String phoneNumber, String address, String relationshipWithStudent,List<Students> student
            ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.relationshipWithStudent = relationshipWithStudent;
        this.student = student;
    }
}
