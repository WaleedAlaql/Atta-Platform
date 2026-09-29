package com.waleed.capstone2.Service;

import com.waleed.capstone2.Api.ApiException;
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

    public void addItem(DonationItem item) {
        Donor donor = donorRepository.findById(item.getDonorId())
                .orElseThrow(() -> new ApiException("Donor not found"));

        item.setDonorPhone(donor.getPhone());
        donationItemRepository.save(item);
    }

    public void updateItem(Integer id, DonationItem donationItem) {
        DonationItem oldDevice = donationItemRepository.findById(id)
                .orElseThrow(() -> new ApiException("Device not found"));

        oldDevice.setTitle(donationItem.getTitle());
        oldDevice.setDescription(donationItem.getDescription());
        oldDevice.setCategory(donationItem.getCategory());
        oldDevice.setCondition(donationItem.getCondition());
        oldDevice.setPickupLocation(donationItem.getPickupLocation());
        oldDevice.setDonorPhone(donationItem.getDonorPhone());

        donationItemRepository.save(oldDevice);
    }

    public void deleteItem(Integer id) {
        DonationItem device = donationItemRepository.findById(id)
                .orElseThrow(() -> new ApiException("Item not found"));
        donationItemRepository.delete(device);
    }

    public DonationItem findById(Integer id) {
        if (id == null) {
            return null;
        }
        return donationItemRepository.findById(id).orElse(null);
    }

    public List<DonationItem> getByCategory(String category) {
        return donationItemRepository.findByCategory(category);
    }

    public List<DonationItem> getByLocation(String location) {
        return donationItemRepository.findByPickupLocationContainingIgnoreCase(location);
    }

    public List<DonationItem> getByDonorId(Integer donorId) {
        if (donorId == null || !donorRepository.existsById(donorId)) {
            throw new ApiException("Donor not found");
        }
        return donationItemRepository.findByDonorId(donorId);
    }

    public Map<String, Object> getPlatformStats() {
        long totalDevices = donationItemRepository.count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDevicesListed", totalDevices);
        return stats;
    }
}