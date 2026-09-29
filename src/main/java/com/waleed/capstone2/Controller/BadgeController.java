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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBadge(@PathVariable Integer id) {
        badgeService.deleteBadge(id);
        return ResponseEntity.ok("Badge deleted successfully");
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<?> getBadgesByDonor(@PathVariable Integer donorId) {
        return ResponseEntity.ok(badgeService.getBadgesByDonor(donorId));
    }
}
