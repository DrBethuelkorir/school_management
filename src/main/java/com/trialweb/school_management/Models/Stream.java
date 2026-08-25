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
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int totalNumberOfStudents;

    @OneToMany(mappedBy = "stream")
    private List<Students> students = new ArrayList<>();
    @ManyToOne()
    @JoinColumn(name = "classId")
    private Classes classes;
    @OneToOne()
    @JoinColumn(name = "teacherId")
    private Teachers teachers;

    public Stream(String name, int totalNumberOfStudents,
                  List<Students> students, Classes classes, Teachers teachers) {
        this.name = name;
        this.totalNumberOfStudents = totalNumberOfStudents;
        this.students = students;
        this.classes = classes;
        this.teachers = teachers;
    }
}