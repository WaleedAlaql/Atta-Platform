package com.waleed.capstone2.Repository;

import com.waleed.capstone2.Entity.DonationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationItemRepository extends JpaRepository<DonationItem, Integer> {
    
    List<DonationItem> findByCategory(String category);

    // this method is used to find the devices that are available for pickup in a specific location
    List<DonationItem> findByPickupLocationContainingIgnoreCase(String pickupLocation);

}