package com.edushare.edushare_backend.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String courseName;

    @ManyToOne
    @JoinColumn(name = "professor_id", referencedColumnName = "userId", nullable = false)  // Ajoute une colonne foreign key vers Professor
    private Professor professor;

    @ManyToMany(mappedBy = "enrolledCourses")
    private List<Student> enrolledStudents=new ArrayList<>();
}
