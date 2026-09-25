package com.waleed.capstone2.Controller;

import com.waleed.capstone2.Service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/openai")
@RequiredArgsConstructor
public class OpenAIController {

    private final GeminiService openAIService;

    // help a donor make a decision (based on the device type, unused duration, and condition)
    @GetMapping("/help-decision")
    public ResponseEntity<String> helpDonorDecide(
            @RequestParam String deviceType,
            @RequestParam String unusedDuration,
            @RequestParam String condition) {

        String advice = openAIService.helpDonorDecide(deviceType, unusedDuration, condition);
        return ResponseEntity.ok(advice);
    }

    // help a donor write a professional ad
    @GetMapping("/generate-ad")
    public ResponseEntity<String> generateAdDescription(
            @RequestParam String itemTitle,
            @RequestParam String itemCondition) {

        String adDescription = openAIService.generateAdDescription(itemTitle, itemCondition);
        return ResponseEntity.ok(adDescription);
    }
}