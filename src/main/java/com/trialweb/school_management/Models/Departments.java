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
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departmentName;

    @OneToMany(mappedBy = "departments")
    private List<Teachers> teachers;
    @OneToMany(mappedBy = "departments")
    private List<Subjects>  subjects;

    public Departments(String departmentName, List<Teachers> teachers, List<Subjects> subjects) {
        this.departmentName = departmentName;
        this.teachers = teachers;
        this.subjects = subjects;
    }
}
