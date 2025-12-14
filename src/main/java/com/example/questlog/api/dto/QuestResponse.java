package com.example.questlog.api.dto;

import com.example.questlog.domain.QuestStatus;

import java.time.Instant;
import java.util.Set;

public class QuestResponse {
    public Long id;
    public String title;
    public String description;
    public QuestStatus status;
    public int priority;
    public Set<String> tags;
    public Instant createdAt;
    public Instant updatedAt;
}
