package com.example.questlog.api;

import com.example.questlog.api.dto.CheckinRequest;
import com.example.questlog.api.dto.CheckinResponse;
import com.example.questlog.api.mapper.CheckinMapper;
import com.example.questlog.service.CheckinService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quests/{questId}/checkins")
public class CheckinApiController {

    private final CheckinService checkinService;

    public CheckinApiController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @GetMapping
    public Page<CheckinResponse> list(
            @PathVariable long questId,
            @PageableDefault(size = 30) Pageable pageable
    ) {
        return checkinService.list(questId, pageable).map(CheckinMapper::toResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckinResponse upsert(@PathVariable long questId, @Valid @RequestBody CheckinRequest req) {
        return CheckinMapper.toResponse(checkinService.upsert(questId, req));
    }
}
