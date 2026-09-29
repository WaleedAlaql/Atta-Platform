package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Api.ApiResponse;
import com.waleed.capstone2.Entity.Rating;
import com.waleed.capstone2.Service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@Valid @RequestBody Rating rating) {
        ratingService.addRating(rating);
        return ResponseEntity.ok("Rating added successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable Integer id) {
        ratingService.deleteRating(id);
        return ResponseEntity.ok("Rating deleted successfully");
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<?> getRatingsByDonor(@PathVariable Integer donorId) {
        return ResponseEntity.ok(ratingService.getRatingsByDonor(donorId));
    }

    @GetMapping("/donor/{donorId}/summary")
    public ResponseEntity<?> getDonorRatingSummary(@PathVariable Integer donorId) {
        String message = ratingService.donorRatingSummary(donorId);
        return ResponseEntity.ok(new ApiResponse(message));
    }
}
