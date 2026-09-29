package com.waleed.capstone2.Service;

import com.waleed.capstone2.Api.ApiException;
import com.waleed.capstone2.Entity.Beneficiary;
import com.waleed.capstone2.Repository.BeneficiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;

    public List<Beneficiary> getAllBeneficiaries() {
        return beneficiaryRepository.findAll();
    }

    public void addBeneficiary(Beneficiary beneficiary) {
        beneficiary.setVerified(false); // the beneficiary starts as unverified until the admin verifies it
        beneficiaryRepository.save(beneficiary);
    }

    public void updateBeneficiary(Integer id, Beneficiary beneficiary) {
        Beneficiary b = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new ApiException("Beneficiary not found"));

        b.setName(beneficiary.getName());
        b.setEmail(beneficiary.getEmail());
        b.setPassword(beneficiary.getPassword());
        b.setDocumentUrl(beneficiary.getDocumentUrl());
        beneficiaryRepository.save(b);
    }

    public void deleteBeneficiary(Integer id) {
        if (!beneficiaryRepository.existsById(id)) {
            throw new ApiException("Beneficiary not found");
        }
        beneficiaryRepository.deleteById(id);
    }
}