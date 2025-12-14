package com.example.questlog.api.mapper;

import com.example.questlog.api.dto.CheckinResponse;
import com.example.questlog.domain.Checkin;

public class CheckinMapper {
    private CheckinMapper() {}

    public static CheckinResponse toResponse(Checkin c) {
        CheckinResponse r = new CheckinResponse();
        r.id = c.getId();
        r.questId = c.getQuest().getId();
        r.occurredOn = c.getOccurredOn();
        r.mood = c.getMood();
        r.note = c.getNote();
        r.createdAt = c.getCreatedAt();
        return r;
    }
}
