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
public class Teachers extends User {

    private String phone;
    private String teacherID;

    @ManyToMany()
    private List<Subjects> subjects = new ArrayList<>();
    @ManyToOne()
    @JoinColumn(name = "departmentId")
    private Departments  departments;


    public Teachers(String phone, String teacherID,
                    List<Subjects> subjects, Departments departments) {
        this.phone = phone;
        this.teacherID = teacherID;
        this.subjects = subjects;
        this.departments = departments;

    }
}
