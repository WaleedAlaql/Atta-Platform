package com.waleed.capstone2.Repository;

import com.waleed.capstone2.Entity.DonationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequest, Integer> {

    // this method is used to find the donation requests for a specific beneficiary
    List<DonationRequest> findByBeneficiaryId(Integer beneficiaryId);
}