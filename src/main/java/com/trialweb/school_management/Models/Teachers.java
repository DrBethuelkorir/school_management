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
@PrimaryKeyJoinColumn(name = "user_id")
@DiscriminatorValue("teacher")
public class Teachers extends User {

    private String phone;
    private String teacherId;

    @ManyToMany()
    private List<Subjects> subjects = new ArrayList<>();
    @ManyToOne()
    @JoinColumn(name = "departmentId")
    private Departments  departments;


    public Teachers(String phone, String teacherId,
                    List<Subjects> subjects, Departments departments) {
        this.phone = phone;
        this.teacherId = teacherId;
        this.subjects = subjects;
        this.departments = departments;

    }
}
