package org.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Subject {

    private String subjectName;
    private Integer semester;
    @Id
    private Long id;
    private Integer coefficient;


}
