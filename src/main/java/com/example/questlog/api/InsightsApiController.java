package com.example.questlog.api;

import com.example.questlog.api.dto.StreakResponse;
import com.example.questlog.service.InsightsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/insights")
public class InsightsApiController {

    private final InsightsService insightsService;

    public InsightsApiController(InsightsService insightsService) {
        this.insightsService = insightsService;
    }

    @GetMapping("/streaks")
    public List<StreakResponse> streaks() {
        return insightsService.streaks();
    }
}
