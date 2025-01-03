package com.edushare.edushare_backend.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String courseName;

    @ManyToOne
    private Professor creator;

    @ManyToMany(mappedBy = "enrolledCourses")
    private List<Student> enrolledStudents;
}
