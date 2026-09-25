package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/badge")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllBadges() {
        return ResponseEntity.ok(badgeService.getAllBadges());
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<?> getBadgesByDonor(@PathVariable Integer donorId) {
        return ResponseEntity.ok(badgeService.getBadgesByDonor(donorId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBadge(@PathVariable Integer id) {
        boolean deleted = badgeService.deleteBadge(id);
        if (!deleted) {
            return ResponseEntity.badRequest().body("Badge not found");
        }
        return ResponseEntity.ok("Badge deleted successfully");
    }
}