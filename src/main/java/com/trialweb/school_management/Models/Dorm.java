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
public class Dorm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long capacity;

    @OneToMany(mappedBy = "dorm")
    private List<Students> students;

    public Dorm(Long capacity, String name,List<Students> students) {
        this.capacity = capacity;
        this.name = name;
        this.students = students;
    }
}