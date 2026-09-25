package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Api.ApiResponse;
import com.waleed.capstone2.Entity.DonationRequest;
import com.waleed.capstone2.Service.DonationRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/donation-request")
@RequiredArgsConstructor
public class DonationRequestController {

    private final DonationRequestService donationRequestService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllRequests() {
        return ResponseEntity.ok(donationRequestService.getAllRequests());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRequest(@Valid @RequestBody DonationRequest request) {
        String result = donationRequestService.addRequest(request);

        if (result.equals("Item not found")) {
            return ResponseEntity.status(404).body(new ApiResponse("Donation item not found"));
        }
        if (result.equals("Beneficiary not found")) {
            return ResponseEntity.status(404).body(new ApiResponse("Beneficiary not found"));
        }
        if (result.equals("Beneficiary account is not verified by admin yet")) {
            return ResponseEntity.status(400).body(new ApiResponse("Beneficiary account is not verified by admin yet"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Donation request created successfully and WhatsApp notification sent"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer id) {
        boolean isDeleted = donationRequestService.deleteRequest(id);
        if (!isDeleted) {
            return ResponseEntity.badRequest().body("Donation request not found");
        }
        return ResponseEntity.ok("Donation request deleted successfully, and Item status restored.");
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateRequestStatus(@PathVariable Integer id) {
        boolean updated = donationRequestService.updateRequestStatus(id);
        if (!updated) {
            return ResponseEntity.badRequest().body("Request not found or already completed");
        }
        return ResponseEntity.ok("Request status updated successfully to the next stage and notification sent.");
    }
}