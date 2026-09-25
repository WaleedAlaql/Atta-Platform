package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Entity.Donor;
import com.waleed.capstone2.Service.DonorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/donor")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllDonors() {
        return ResponseEntity.ok(donorService.getAllDonors());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDonor(@Valid @RequestBody Donor donor, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        donorService.addDonor(donor);
        return ResponseEntity.ok("Donor added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDonor(@PathVariable Integer id, @Valid @RequestBody Donor donor, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = donorService.updateDonor(id, donor);
        if (!isUpdated) {
            return ResponseEntity.badRequest().body("Donor not found");
        }
        return ResponseEntity.ok("Donor updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDonor(@PathVariable Integer id) {
        boolean isDeleted = donorService.deleteDonor(id);
        if (!isDeleted) {
            return ResponseEntity.badRequest().body("Donor not found");
        }
        return ResponseEntity.ok("Donor deleted successfully");
    }
}