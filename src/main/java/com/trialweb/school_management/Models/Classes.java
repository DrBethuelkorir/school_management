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
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String totalNumberOfStudents;

    @OneToMany(mappedBy = "classes")
    private List<Students> students;
    @OneToMany(mappedBy = "classes")
    private List<Stream> stream;

    public Classes(String name, String totalNumberOfStudents,
                   List<Students> students, List<Stream> stream) {
        this.name = name;
        this.totalNumberOfStudents = totalNumberOfStudents;
        this.students = students;
        this.stream = stream;
    }
}