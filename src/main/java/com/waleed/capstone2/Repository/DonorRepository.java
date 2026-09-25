package com.waleed.capstone2.Repository;

import com.waleed.capstone2.Entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Integer> {
    Donor findByEmail(String email);
}