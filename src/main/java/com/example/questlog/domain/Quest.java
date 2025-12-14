package com.example.questlog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "quests")
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 140)
    @Column(nullable = false, length = 140)
    private String title;

    @Size(max = 2000)
    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private QuestStatus status = QuestStatus.ACTIVE;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private int priority = 3;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "quest_tags", joinColumns = @JoinColumn(name = "quest_id"))
    @Column(name = "tag", length = 40, nullable = false)
    private Set<String> tags = new LinkedHashSet<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Quest() {}

    public Quest(String title, String description, QuestStatus status, int priority, Set<String> tags) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        if (tags != null) this.tags = new LinkedHashSet<>(tags);
    }

    // Getters & setters

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public QuestStatus getStatus() { return status; }
    public void setStatus(QuestStatus status) { this.status = status; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) {
        this.tags = (tags == null) ? new LinkedHashSet<>() : new LinkedHashSet<>(tags);
    }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quest quest)) return false;
        return id != null && Objects.equals(id, quest.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
