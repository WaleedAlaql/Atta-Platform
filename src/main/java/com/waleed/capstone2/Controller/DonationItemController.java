package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Api.ApiResponse;
import com.waleed.capstone2.Entity.DonationItem;
import com.waleed.capstone2.Service.DonationItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/device")
@RequiredArgsConstructor
public class DonationItemController {

    private final DonationItemService donationItemService;

    @GetMapping("/get")
    public ResponseEntity<List<DonationItem>> getAllItems() {
        return ResponseEntity.status(200).body(donationItemService.getAllItems());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@Valid @RequestBody DonationItem donationItem) {
        String response = donationItemService.addItem(donationItem);

        // if the donor is not found
        if (response.equals("Donor not found")) {
            return ResponseEntity.status(404).body(new ApiResponse("Donor not found with the given ID"));
        }

        // if the item is added successfully
        return ResponseEntity.status(200).body(new ApiResponse("Item added successfully with direct phone contact"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateItem(@PathVariable Integer id, @Valid @RequestBody DonationItem donationItem) {
        boolean updated = donationItemService.updateItem(id, donationItem);
        if (!updated) {
            return ResponseEntity.status(400).body(new ApiResponse("Device not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Device updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Integer id) {
        boolean deleted = donationItemService.deleteItem(id);
        if (!deleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Item not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Item deleted successfully"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<DonationItem>> getByCategory(@PathVariable String category) {
        return ResponseEntity.status(200).body(donationItemService.getByCategory(category));
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<DonationItem>> getByLocation(@PathVariable String location) {
        return ResponseEntity.status(200).body(donationItemService.getByLocation(location));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.status(200).body(donationItemService.getPlatformStats());
    }
}