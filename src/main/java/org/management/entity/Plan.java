package org.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Plan {

    private String week;
    @OneToMany
    private List<Course> courseList;
    @Id
    private Long id;

}
