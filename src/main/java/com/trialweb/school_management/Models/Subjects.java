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
            private String subjectCode;

            @ManyToMany(mappedBy = "subjects")
            private List<Students> students = new ArrayList<>();
            @ManyToMany()
            @JoinTable(
                    name = "teachers_subjects",
                    joinColumns = @JoinColumn(name = "subject_id"),
                    inverseJoinColumns = @JoinColumn(name = "teacher_id")

            )
            private List<Teachers> teachers = new ArrayList<>();
            @ManyToOne()
            @JoinColumn(name = "subjectId")
            private Departments departments;

            public Subjects(String subjectName,String subjectCode,
                            List<Students> students, List<Teachers> teachers, Departments departments) {
                this.subjectName = subjectName;
                this.subjectCode = subjectCode;
                this.students = students;
                this.teachers = teachers;
                this.departments = departments;
            }
        }
