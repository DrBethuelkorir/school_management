package com.trialweb.school_management.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departmentName;
    private String description;

    @OneToMany(mappedBy = "department")
    private List<Staff> staff = new ArrayList<>();

    @OneToMany(mappedBy = "departments")
    private List<Teachers> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "departments")
    private List<Subjects> subjects = new ArrayList<>();

    public Departments(String departmentName, List<Teachers> teachers, List<Subjects> subjects) {
        this.departmentName = departmentName;
        this.teachers = teachers;
        this.subjects = subjects;
    }
}