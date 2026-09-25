package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Entity.Admin;
import com.waleed.capstone2.Entity.Badge;
import com.waleed.capstone2.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
    public ResponseEntity<?> addAdmin(@Valid @RequestBody Admin admin, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        adminService.addAdmin(admin);
        return ResponseEntity.ok("Admin added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @Valid @RequestBody Admin admin, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = adminService.updateAdmin(id, admin);
        if (!isUpdated){
            return ResponseEntity.badRequest().body("Admin not found");
        }
        return ResponseEntity.ok("Admin updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {
        boolean isDeleted = adminService.deleteAdmin(id);
        if (!isDeleted) {
            return ResponseEntity.badRequest().body("Admin not found");
        }
        return ResponseEntity.ok("Admin deleted successfully");
    }

    // verify a beneficiary account
    @PutMapping("/verify-beneficiary/{id}")
    public ResponseEntity<String> verifyBeneficiary(@PathVariable Integer id) {
        String result = adminService.verifyBeneficiary(id);

        // if the account is not found
        if (result.equals("Beneficiary not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        // if the account is already verified
        if (result.equals("Beneficiary account is already verified!")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        // if the account is verified successfully
        return ResponseEntity.ok(result);
    }

    // give a badge to a donor
    @PostMapping("/give-badge/{donorId}/{badgeTitle}")
    public ResponseEntity<?> giveBadgeToDonor(@PathVariable Integer donorId, @PathVariable String badgeTitle) {
        boolean added = adminService.giveBadgeToDonor(donorId, badgeTitle);
        if (!added) {
            return ResponseEntity.badRequest().body("Donor not found");
        }
        return ResponseEntity.ok("Badge '" + badgeTitle + "' awarded to donor successfully");
    }
}