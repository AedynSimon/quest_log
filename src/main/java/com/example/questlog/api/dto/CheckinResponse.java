package com.example.questlog.api.dto;

import com.example.questlog.domain.Mood;

import java.time.Instant;
import java.time.LocalDate;

public class CheckinResponse {
    public Long id;
    public Long questId;
    public LocalDate occurredOn;
    public Mood mood;
    public String note;
    public Instant createdAt;
}
