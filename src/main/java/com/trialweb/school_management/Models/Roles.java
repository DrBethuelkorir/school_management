package com.trialweb.school_management.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;

    @OneToOne()
    @JoinColumn(name = "student_id")
    private Students student;
    @OneToOne()
    @JoinColumn(name = "parent_id")
    private Parent parent;
    @OneToOne()
    @JoinColumn(name = "staff_id")
    private Staff staff;
    @ManyToMany(mappedBy = "roles")
    private List<Teachers> teachers = new ArrayList<>();

    public Roles(String roleName, Students student,
                 Parent parent, Staff staff, List<Teachers> teachers) {
        this.roleName = roleName;
        this.student = student;
        this.parent = parent;
        this.staff = staff;
        this.teachers = teachers;
    }
}