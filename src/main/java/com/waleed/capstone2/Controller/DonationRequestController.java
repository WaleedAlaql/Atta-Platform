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
        donationRequestService.addRequest(request);
        return ResponseEntity.status(200).body(new ApiResponse("Donation request created successfully and WhatsApp notification sent"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer id) {
        donationRequestService.deleteRequest(id);
        return ResponseEntity.ok("Donation request deleted successfully, and Item status restored.");
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateRequestStatus(@PathVariable Integer id) {
        donationRequestService.updateRequestStatus(id);
        return ResponseEntity.ok("Request status updated successfully to the next stage and notification sent.");
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getRequestsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(donationRequestService.getRequestsByStatus(status));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countRequests() {
        return ResponseEntity.ok(donationRequestService.countRequests());
    }
}
