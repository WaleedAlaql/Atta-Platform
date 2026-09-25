package com.waleed.capstone2.Service;

import com.waleed.capstone2.Entity.Rating;
import com.waleed.capstone2.Repository.BeneficiaryRepository;
import com.waleed.capstone2.Repository.DonorRepository;
import com.waleed.capstone2.Repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final DonorRepository donorRepository;
    private final BeneficiaryRepository beneficiaryRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingsByDonor(Integer donorId) {
        return ratingRepository.findByDonorId(donorId);
    }

    public boolean addRating(Rating rating) {
        if (!donorRepository.existsById(rating.getDonorId()) || !beneficiaryRepository.existsById(rating.getBeneficiaryId())) {
            return false; // check if the donor and beneficiary are in the system
        }
        ratingRepository.save(rating);
        return true;
    }

    public boolean deleteRating(Integer id) {
        if (!ratingRepository.existsById(id)) {
            return false;
        }
        ratingRepository.deleteById(id);
        return true;
    }
}