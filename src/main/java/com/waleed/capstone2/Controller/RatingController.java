package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Api.ApiResponse;
import com.waleed.capstone2.Entity.Rating;
import com.waleed.capstone2.Service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<?> getRatingsByDonor(@PathVariable Integer donorId) {
        return ResponseEntity.ok(ratingService.getRatingsByDonor(donorId));
    }

    @GetMapping("/donor/{donorId}/summary")
    public ResponseEntity<?> getDonorRatingSummary(@PathVariable Integer donorId) {
        if (!ratingService.donorExists(donorId)) {
            return ResponseEntity.status(404).body(new ApiResponse("Donor not found"));
        }
        List<Rating> ratings = ratingService.getRatingsByDonor(donorId);
        int count = ratings.size();
        int totalScore = 0;
        for (Rating rating : ratings) {
            totalScore += rating.getScore();
        }
        double average = count == 0 ? 0.0 : (double) totalScore / count;
        double averageScore = Math.round(average * 10.0) / 10.0;
        String message = "Donor " + donorId + " has " + count + " ratings with average score " + averageScore;
        return ResponseEntity.ok(new ApiResponse(message));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@Valid @RequestBody Rating rating, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        boolean added = ratingService.addRating(rating);
        if (!added) {
            return ResponseEntity.badRequest().body("Donor or Beneficiary not found");
        }
        return ResponseEntity.ok("Rating added successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable Integer id) {
        boolean deleted = ratingService.deleteRating(id);
        if (!deleted) {
            return ResponseEntity.badRequest().body("Rating not found");
        }
        return ResponseEntity.ok("Rating deleted successfully");
    }
}