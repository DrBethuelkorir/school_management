        package com.trialweb.school_management.Models;

        import jakarta.persistence.*;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.RequiredArgsConstructor;
        import lombok.Setter;

        import java.util.ArrayList;
        import java.util.List;


        @Entity
        @Getter
        @Setter
        @NoArgsConstructor
        public class Students extends User {

           private int age;
           private String adm;


           //relationships
            @ManyToOne()
            @JoinColumn(name = "classId")
            private Classes classes;
            @ManyToOne()
            @JoinColumn(name = "streamId")
            private Stream stream;
            @ManyToOne()
            @JoinColumn(name = "dormId")
            private Dorm dorm;
            @ManyToOne(cascade = CascadeType.ALL)
            @JoinColumn(name = "parentId")
            private Parent parent;
            @ManyToMany()
            @JoinTable(
                    name = "Student_subject",
                    joinColumns = @JoinColumn(name = "student_id"),
                    inverseJoinColumns = @JoinColumn(name = "subject_id")
            )
            private List<Subjects> subjects = new ArrayList<>();
            @OneToMany(mappedBy = "students", cascade = CascadeType.ALL)
            private List<Fee> fee = new ArrayList<>();


            public Students(int age, Classes classes, Stream stream, Dorm dorm,
                            Parent parent, List<Subjects> subjects, List<Fee> fee) {
                this.age = age;
                this.classes = classes;
                this.stream = stream;
                this.dorm = dorm;
                this.parent = parent;
                this.subjects = subjects;
                this.fee = fee;
            }
        }
