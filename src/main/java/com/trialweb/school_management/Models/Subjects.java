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
        public class Subjects {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;
            private String subjectName;

            @ManyToMany(mappedBy = "subjects")
            private List<Students> students = new ArrayList<>();
            @ManyToOne()
            @JoinColumn(name = "teacher_id")
            private Teachers teachers;
            @ManyToOne()
            @JoinColumn(name = "subject_id")
            private Departments departments;

            public Subjects(String subjectName, List<Students> students, Teachers teachers, Departments departments) {
                this.subjectName = subjectName;
                this.students = students;
                this.teachers = teachers;
                this.departments = departments;
            }
        }
