package org.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Absence {
    private LocalDate absenceDate;
    private Boolean justified;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;
    @Id
    private Long id;
    private Boolean eliminated;
}
