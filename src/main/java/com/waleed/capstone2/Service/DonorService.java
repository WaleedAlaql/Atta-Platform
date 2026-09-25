package com.waleed.capstone2.Service;

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

    public boolean updateDonor(Integer id, Donor donor) {
        Donor old = donorRepository.findById(id).orElse(null);
        if (old == null) {
            return false;
        }

        old.setName(donor.getName());
        old.setEmail(donor.getEmail());
        old.setPassword(donor.getPassword());
        old.setPhone(donor.getPhone());
        donorRepository.save(old);
        return true;
    }

    public boolean deleteDonor(Integer id) {
        if (!donorRepository.existsById(id)) {
            return false;
        }
        donorRepository.deleteById(id);
        return true;
    }
}