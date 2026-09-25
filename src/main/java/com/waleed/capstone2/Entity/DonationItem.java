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
@Table(name = "donation_items")
public class DonationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    @Column(nullable = false, length = 500)
    private String description;

    @NotEmpty(message = "Category cannot be empty")
    @Pattern(regexp = "Laptop|Tablet|Books|Clothes|Stationery|Other", message = "Category must be Laptop, Tablet, Books, Clothes, Stationery, or Other")    @Column(nullable = false)
    private String category; // Laptop, Tablet, Books, Clothes, Stationery, Other

    @NotEmpty(message = "Condition cannot be empty")
    @Pattern(regexp = "Excellent|Good|Needs Maintenance", message = "Condition must be Excellent, Good, or Needs Maintenance")
    @Column(name = "item_condition", nullable = false)
    private String condition; // Excellent, Good, Needs Maintenance

    @NotEmpty(message = "Pickup location cannot be empty")
    @Column(nullable = false)
    private String pickupLocation;

    @Column(nullable = false)
    private String donorPhone;

    @NotNull(message = "Donor ID cannot be null")
    @Column(nullable = false)
    private Integer donorId;
}