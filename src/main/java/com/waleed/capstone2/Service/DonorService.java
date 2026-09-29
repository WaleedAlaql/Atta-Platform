package com.waleed.capstone2.Service;

import com.waleed.capstone2.Api.ApiException;
import com.waleed.capstone2.Entity.Donor;
import com.waleed.capstone2.Repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonorService {

    private final DonorRepository donorRepository;

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public void addDonor(Donor donor) {
        donorRepository.save(donor);
    }

    public void updateDonor(Integer id, Donor donor) {
        Donor old = donorRepository.findById(id)
                .orElseThrow(() -> new ApiException("Donor not found"));

        old.setName(donor.getName());
        old.setEmail(donor.getEmail());
        old.setPassword(donor.getPassword());
        old.setPhone(donor.getPhone());
        donorRepository.save(old);
    }

    public void deleteDonor(Integer id) {
        if (!donorRepository.existsById(id)) {
            throw new ApiException("Donor not found");
        }
        donorRepository.deleteById(id);
    }
}