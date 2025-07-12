package org.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Grade {
    @Id
    private Long id;
    private String subjectName;
    private Double value;
    private LocalDate examDate;
}
