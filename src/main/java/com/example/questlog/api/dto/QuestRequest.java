package com.example.questlog.api.dto;

import com.example.questlog.domain.QuestStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

public class QuestRequest {
    @NotBlank
    @Size(max = 140)
    public String title;

    @Size(max = 2000)
    public String description;

    public QuestStatus status = QuestStatus.ACTIVE;

    @Min(1)
    @Max(5)
    public int priority = 3;

    public Set<String> tags = new LinkedHashSet<>();

    // Thymeleaf + Spring form binding expects standard JavaBean getters/setters.
    // We keep public fields for concise JSON mapping, but add accessors for template binding.
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public void setStatus(QuestStatus status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = (tags == null) ? new LinkedHashSet<>() : tags;
    }
}
