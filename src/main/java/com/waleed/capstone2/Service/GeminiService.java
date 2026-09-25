package com.waleed.capstone2.Service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final String GEMINI_API_KEY = "AQ.Ab8RN6Kes1YFKL6KHt_RVpCS-SRsFlIbt5Uss1rIQar_c9fJ1w";

    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.6-flash:generateContent?key=" + GEMINI_API_KEY;

    public String helpDonorDecide(String itemType, String unusedDuration, String condition) {
        String prompt = "As a charitable consultant for a donation platform, do you think a " + itemType +
                " that has been unused for " + unusedDuration + " with a condition of " + condition +
                " is suitable for a needy beneficiary? Answer briefly and professionally in English.";

        return callGeminiApi(prompt);
    }

    public String generateAdDescription(String itemTitle, String itemCondition) {
        String prompt = "Write a short, engaging, and professional marketing ad description for a donation item titled: " + itemTitle +
                " with condition: " + itemCondition + " to encourage donors and beneficiaries. Answer in English.";

        return callGeminiApi(prompt);
    }

    private String callGeminiApi(String promptText) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> partMap = new HashMap<>();
            partMap.put("text", promptText);

            Map<String, Object> contentMap = new HashMap<>();
            contentMap.put("parts", Collections.singletonList(partMap));

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", Collections.singletonList(contentMap));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(GEMINI_URL, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map responseBody = response.getBody();
                List<Map> candidates = (List<Map>) responseBody.get("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    Map content = (Map) candidates.get(0).get("content");
                    List<Map> parts = (List<Map>) content.get("parts");
                    if (parts != null && !parts.isEmpty()) {
                        return (String) parts.get(0).get("text");
                    }
                }
            }
            return "Sorry, could not generate a response at this moment.";
        } catch (Exception e) {
            return "Error connecting to AI service: " + e.getMessage();
        }
    }
}