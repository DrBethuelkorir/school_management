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
public class Teachers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String teacherID;

    @OneToMany(mappedBy = "teachers")
    private List<Subjects> subjects = new ArrayList<>();
    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Departments  departments;
    @ManyToMany()
    @JoinTable(
            name = "teacher_roles",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Roles> roles = new ArrayList<>();

    public Teachers(String firstName, String lastName, String email, String password, String phone,
                    String teacherID, List<Subjects> subjects, Departments departments,List<Roles> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.teacherID = teacherID;
        this.subjects = subjects;
        this.departments = departments;
        this.roles = roles;
    }
}
