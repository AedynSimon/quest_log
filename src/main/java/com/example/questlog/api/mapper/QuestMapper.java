package com.example.questlog.api.mapper;

import com.example.questlog.api.dto.QuestResponse;
import com.example.questlog.domain.Quest;

public class QuestMapper {
    private QuestMapper() {}

    public static QuestResponse toResponse(Quest q) {
        QuestResponse r = new QuestResponse();
        r.id = q.getId();
        r.title = q.getTitle();
        r.description = q.getDescription();
        r.status = q.getStatus();
        r.priority = q.getPriority();
        r.tags = q.getTags();
        r.createdAt = q.getCreatedAt();
        r.updatedAt = q.getUpdatedAt();
        return r;
    }
}
