package com.example.questlog.api.dto;

import com.example.questlog.domain.Mood;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CheckinRequest {
    @NotNull
    public LocalDate occurredOn;

    public Mood mood = Mood.OK;

    @Size(max = 500)
    public String note;

    // Thymeleaf + Spring form binding expects standard JavaBean getters/setters.
    public LocalDate getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(LocalDate occurredOn) {
        this.occurredOn = occurredOn;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
