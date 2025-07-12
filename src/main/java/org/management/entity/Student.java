package org.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Student {
    @jakarta.persistence.Id
    @Id
    private Long id;
    private String lastName;

    private String firstName;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Grade> grades;


}
