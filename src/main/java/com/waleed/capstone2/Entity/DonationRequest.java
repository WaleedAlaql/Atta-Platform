package com.waleed.capstone2.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donation_requests")
public class DonationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate requestDate;

    @Pattern(regexp = "CREATED|IN_PROGRESS|DELIVERED", message = "Status must be CREATED, IN_PROGRESS, or DELIVERED")
    private String status = "CREATED"; // CREATED -> IN_PROGRESS -> DELIVERED

    @NotNull(message = "Beneficiary ID cannot be null")
    @Column(nullable = false)
    private Integer beneficiaryId;

    @NotNull(message = "Item ID cannot be null")
    @Column(nullable = false)
    private Integer itemId;
}