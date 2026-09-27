package com.waleed.capstone2.Repository;

import com.waleed.capstone2.Entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {
    Beneficiary findByEmail(String email);

    List<Beneficiary> findByIsVerifiedFalse();
}