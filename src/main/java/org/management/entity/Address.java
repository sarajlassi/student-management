package org.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String addressLine1;
    private String addressLine2;
    @Id
    private Long id;
}
