package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Entity.Admin;
import com.waleed.capstone2.Entity.Badge;
import com.waleed.capstone2.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@Valid @RequestBody Admin admin) {
        adminService.addAdmin(admin);
        return ResponseEntity.ok("Admin added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @Valid @RequestBody Admin admin) {
        adminService.updateAdmin(id, admin);
        return ResponseEntity.ok("Admin updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }

    @GetMapping("/unverified")
    public ResponseEntity<?> getUnverifiedBeneficiaries() {
        return ResponseEntity.ok(adminService.getUnverifiedBeneficiaries());
    }

    // verify a beneficiary account
    @PutMapping("/verify-beneficiary/{id}")
    public ResponseEntity<String> verifyBeneficiary(@PathVariable Integer id) {
        adminService.verifyBeneficiary(id);
        return ResponseEntity.ok("Beneficiary verified successfully");
    }

    @PostMapping("/give-badge")
    public ResponseEntity<?> giveBadge(@Valid @RequestBody Badge badge) {
        adminService.giveBadge(badge);
        return ResponseEntity.ok("Badge '" + badge.getTitle() + "' awarded to donor successfully");
    }
}
