package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Entity.Beneficiary;
import com.waleed.capstone2.Service.BeneficiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/beneficiary")
@RequiredArgsConstructor
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllBeneficiaries() {
        return ResponseEntity.ok(beneficiaryService.getAllBeneficiaries());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBeneficiary(@Valid @RequestBody Beneficiary beneficiary, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        beneficiaryService.addBeneficiary(beneficiary);
        return ResponseEntity.ok("Beneficiary registered successfully and pending verification");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBeneficiary(@PathVariable Integer id, @Valid @RequestBody Beneficiary beneficiary, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        boolean updated = beneficiaryService.updateBeneficiary(id, beneficiary);
        if (!updated) {
            return ResponseEntity.badRequest().body("Beneficiary not found");
        }
        return ResponseEntity.ok("Beneficiary updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBeneficiary(@PathVariable Integer id) {
        boolean isDeleted = beneficiaryService.deleteBeneficiary(id);
        if (!isDeleted) {
            return ResponseEntity.badRequest().body("Beneficiary not found");
        }
        return ResponseEntity.ok("Beneficiary deleted successfully");
    }
}