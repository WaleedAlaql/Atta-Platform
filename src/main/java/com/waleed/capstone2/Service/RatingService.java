package com.waleed.capstone2.Service;

import com.waleed.capstone2.Api.ApiException;
import com.waleed.capstone2.Entity.Donor;
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

    public void addRating(Rating rating) {
        if (!donorRepository.existsById(rating.getDonorId()) || !beneficiaryRepository.existsById(rating.getBeneficiaryId())) {
            throw new ApiException("Donor or Beneficiary not found");
        }
        ratingRepository.save(rating);
    }

    public void deleteRating(Integer id) {
        if (!ratingRepository.existsById(id)) {
            throw new ApiException("Rating not found");
        }
        ratingRepository.deleteById(id);
    }

    public List<Rating> getRatingsByDonor(Integer donorId) {
        return ratingRepository.findByDonorId(donorId);
    }

    public boolean donorExists(Integer donorId) {
        return donorId != null && donorRepository.existsById(donorId);
    }

    public String donorRatingSummary(Integer donorId) {
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new ApiException("Donor not found"));
        List<Rating> ratings = ratingRepository.findByDonorId(donorId);
        int count = ratings.size();
        int totalScore = 0;
        for (Rating rating : ratings) {
            totalScore += rating.getScore();
        }
        double average = count == 0 ? 0.0 : (double) totalScore / count;
        double averageScore = Math.round(average * 10.0) / 10.0;
        return donor.getName() + " has " + count + " ratings with average score " + averageScore;
    }
}
