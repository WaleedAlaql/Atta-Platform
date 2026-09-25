package com.waleed.capstone2.Repository;

import com.waleed.capstone2.Entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {
    Beneficiary findByEmail(String email);
}