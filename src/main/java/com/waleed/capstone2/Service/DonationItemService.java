package com.waleed.capstone2.Service;

import com.waleed.capstone2.Entity.DonationItem;
import com.waleed.capstone2.Entity.Donor;
import com.waleed.capstone2.Repository.DonationItemRepository;
import com.waleed.capstone2.Repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DonationItemService {

    private final DonationItemRepository donationItemRepository;
    private final DonorRepository donorRepository;


    public List<DonationItem> getAllItems() {
        return donationItemRepository.findAll();
    }

    public String addItem(DonationItem item) {
        // check if the donor is in the system
        Donor donor = donorRepository.findById(item.getDonorId()).orElse(null);
        if (donor == null) {
            return "Donor not found";
        }

        // get the donor's phone number and set it to the item
        item.setDonorPhone(donor.getPhone());

        // save the item
        donationItemRepository.save(item);
        return "Donation item added successfully!";
    }

    public boolean updateItem(Integer id, DonationItem donationItem) {
        DonationItem oldDevice = donationItemRepository.findById(id).orElse(null);
        if (oldDevice == null) {
            return false;
        }

        oldDevice.setTitle(donationItem.getTitle());
        oldDevice.setDescription(donationItem.getDescription());
        oldDevice.setCategory(donationItem.getCategory());
        oldDevice.setCondition(donationItem.getCondition());
        oldDevice.setPickupLocation(donationItem.getPickupLocation());
        oldDevice.setDonorPhone(donationItem.getDonorPhone());

        donationItemRepository.save(oldDevice);
        return true;
    }

    public boolean deleteItem(Integer id) {
        DonationItem device = donationItemRepository.findById(id).orElse(null);
        if (device == null) {
            return false;
        }
        donationItemRepository.delete(device);
        return true;
    }

    public List<DonationItem> getByCategory(String category) {
        return donationItemRepository.findByCategory(category);
    }

    public List<DonationItem> getByLocation(String location) {
        return donationItemRepository.findByPickupLocationContainingIgnoreCase(location);
    }

    public Map<String, Object> getPlatformStats() {
        long totalDevices = donationItemRepository.count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDevicesListed", totalDevices);
        return stats;
    }
}