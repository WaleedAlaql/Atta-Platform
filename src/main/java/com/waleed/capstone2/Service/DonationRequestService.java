package com.waleed.capstone2.Service;

import com.waleed.capstone2.Api.ApiException;
import com.waleed.capstone2.Entity.Beneficiary;
import com.waleed.capstone2.Entity.DonationItem;
import com.waleed.capstone2.Entity.DonationRequest;
import com.waleed.capstone2.Repository.BeneficiaryRepository;
import com.waleed.capstone2.Repository.DonationItemRepository;
import com.waleed.capstone2.Repository.DonationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationRequestService {

    private final DonationRequestRepository donationRequestRepository;
    private final DonationItemRepository donationItemRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final WhatsAppService whatsAppService;

    public List<DonationRequest> getAllRequests() {
        return donationRequestRepository.findAll();
    }

    public void addRequest(DonationRequest request) {
        DonationItem item = donationItemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ApiException("Item not found"));

        Beneficiary beneficiary = beneficiaryRepository.findById(request.getBeneficiaryId())
                .orElseThrow(() -> new ApiException("Beneficiary not found"));

        if (!beneficiary.isVerified()) {
            throw new ApiException("Beneficiary account is not verified by admin yet");
        }

        if (donationRequestRepository.existsByBeneficiaryIdAndItemId(
                request.getBeneficiaryId(), request.getItemId())) {
            throw new ApiException("Already requested");
        }

        request.setRequestDate(LocalDate.now());
        request.setStatus("CREATED");

        donationRequestRepository.save(request);

        String message = "Hello " + beneficiary.getName() + ", your donation request for (" + item.getTitle() + ") has been *CREATED* successfully! We will keep you updated as the status changes.";
        whatsAppService.sendWhatsAppMessage(beneficiary.getPhone(), message);
    }

    public void updateRequestStatus(Integer requestId) {
        DonationRequest request = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new ApiException("Request not found"));

        String currentStatus = request.getStatus();
        String nextStatus;

        if ("CREATED".equalsIgnoreCase(currentStatus)) {
            nextStatus = "IN_PROGRESS";
        } else if ("IN_PROGRESS".equalsIgnoreCase(currentStatus)) {
            nextStatus = "DELIVERED";
        } else if ("DELIVERED".equalsIgnoreCase(currentStatus)) {
            throw new ApiException("Request already completed");
        } else {
            nextStatus = "CREATED";
        }

        request.setStatus(nextStatus);
        donationRequestRepository.save(request);

        DonationItem item = donationItemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ApiException("Item not found"));
        Beneficiary beneficiary = beneficiaryRepository.findById(request.getBeneficiaryId()).orElse(null);
        if (beneficiary != null && beneficiary.getPhone() != null) {
            String message = "Hello " + beneficiary.getName() + ", your donation request for item: (" + item.getTitle() + ") status has been updated to: *" + nextStatus + "*.";
            whatsAppService.sendWhatsAppMessage(beneficiary.getPhone(), message);
        }
    }

    public void deleteRequest(Integer id) {
        if (!donationRequestRepository.existsById(id)) {
            throw new ApiException("Donation request not found");
        }
        donationRequestRepository.deleteById(id);
    }

    public List<DonationRequest> getRequestsByStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new ApiException("Invalid status. Use CREATED, IN_PROGRESS, or DELIVERED");
        }
        String normalized = status.trim();
        if (!normalized.equalsIgnoreCase("CREATED")
                && !normalized.equalsIgnoreCase("IN_PROGRESS")
                && !normalized.equalsIgnoreCase("DELIVERED")) {
            throw new ApiException("Invalid status. Use CREATED, IN_PROGRESS, or DELIVERED");
        }
        return donationRequestRepository.findByStatusIgnoreCase(normalized);
    }

    public long countRequests() {
        return donationRequestRepository.count();
    }

    public DonationRequest findById(Integer id) {
        if (id == null) {
            return null;
        }
        return donationRequestRepository.findById(id).orElse(null);
    }
}
