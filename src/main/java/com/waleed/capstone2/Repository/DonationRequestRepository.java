package com.waleed.capstone2.Repository;

import com.waleed.capstone2.Entity.DonationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequest, Integer> {

    List<DonationRequest> findByStatusIgnoreCase(String status);

    boolean existsByBeneficiaryIdAndItemId(Integer beneficiaryId, Integer itemId);
}