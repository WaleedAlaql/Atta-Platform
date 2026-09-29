package com.waleed.capstone2.Service;

import com.waleed.capstone2.Api.ApiException;
import com.waleed.capstone2.Entity.Badge;
import com.waleed.capstone2.Repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public void deleteBadge(Integer id) {
        if (!badgeRepository.existsById(id)) {
            throw new ApiException("Badge not found");
        }
        badgeRepository.deleteById(id);
    }

    public List<Badge> getBadgesByDonor(Integer donorId) {
        return badgeRepository.findByDonorId(donorId);
    }
}
