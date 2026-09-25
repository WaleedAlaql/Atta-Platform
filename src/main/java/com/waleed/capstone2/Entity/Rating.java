package com.waleed.capstone2.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Score cannot be null")
    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 5, message = "Score must be at most 5")
    @Column(nullable = false)
    private Integer score; // from 1 to 5

    private String comment;

    @NotNull(message = "Donor ID cannot be null")
    @Column(nullable = false)
    private Integer donorId;

    @NotNull(message = "Beneficiary ID cannot be null")
    @Column(nullable = false)
    private Integer beneficiaryId;
}