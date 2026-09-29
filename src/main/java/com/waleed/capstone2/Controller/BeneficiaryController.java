package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Entity.Beneficiary;
import com.waleed.capstone2.Service.BeneficiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addBeneficiary(@Valid @RequestBody Beneficiary beneficiary) {
        beneficiaryService.addBeneficiary(beneficiary);
        return ResponseEntity.ok("Beneficiary registered successfully and pending verification");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBeneficiary(@PathVariable Integer id, @Valid @RequestBody Beneficiary beneficiary) {
        beneficiaryService.updateBeneficiary(id, beneficiary);
        return ResponseEntity.ok("Beneficiary updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBeneficiary(@PathVariable Integer id) {
        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.ok("Beneficiary deleted successfully");
    }
}
