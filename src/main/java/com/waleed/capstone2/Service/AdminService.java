package com.waleed.capstone2.Service;

import com.waleed.capstone2.Api.ApiException;
import com.waleed.capstone2.Entity.Admin;
import com.waleed.capstone2.Entity.Badge;
import com.waleed.capstone2.Entity.Beneficiary;
import com.waleed.capstone2.Repository.AdminRepository;
import com.waleed.capstone2.Repository.BadgeRepository;
import com.waleed.capstone2.Repository.BeneficiaryRepository;
import com.waleed.capstone2.Repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final BadgeRepository badgeRepository;
    private final DonorRepository donorRepository;
    private final EmailService emailService;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public void updateAdmin(Integer id, Admin admin) {
        Admin old = adminRepository.findById(id)
                .orElseThrow(() -> new ApiException("Admin not found"));

        old.setName(admin.getName());
        old.setEmail(admin.getEmail());
        old.setPassword(admin.getPassword());
        adminRepository.save(old);
    }

    public void deleteAdmin(Integer id) {
        if (!adminRepository.existsById(id)) {
            throw new ApiException("Admin not found");
        }
        adminRepository.deleteById(id);
    }

    public List<Beneficiary> getUnverifiedBeneficiaries() {
        return beneficiaryRepository.findByIsVerifiedFalse();
    }

    public void verifyBeneficiary(Integer beneficiaryId) {
        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId)
                .orElseThrow(() -> new ApiException("Beneficiary not found"));

        if (beneficiary.isVerified()) {
            throw new ApiException("Beneficiary account is already verified!");
        }

        beneficiary.setVerified(true);
        beneficiaryRepository.save(beneficiary);

        if (beneficiary.getEmail() != null) {
            String subject = "Account Verified Successfully";
            String message = "Dear " + beneficiary.getName() + ",\n\n" +
                    "We are glad to inform you that your beneficiary account has been successfully verified by the administration. " +
                    "You can now submit and manage your donation requests smoothly.\n\n" +
                    "Best regards,\n" +
                    "Atta Team";

            emailService.sendEmail(beneficiary.getEmail(), subject, message);
        }
    }

    public void giveBadge(Badge badge) {
        if (badge == null || badge.getDonorId() == null || !donorRepository.existsById(badge.getDonorId())) {
            throw new ApiException("Donor not found");
        }
        badgeRepository.save(badge);
    }
}