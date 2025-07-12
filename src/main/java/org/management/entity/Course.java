package org.management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Course {
    @Id
    private Long id;
    private String courseTitle;
    private LocalDate startDateCourse;
    private LocalDate endDateCourse;
    private Integer numberOfClass;
    private String duration;
    @ManyToMany
    private List<Student> students;

    @ManyToOne
    private Subject subject;
}
