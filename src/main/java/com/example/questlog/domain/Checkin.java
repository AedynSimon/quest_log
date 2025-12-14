package com.example.questlog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "checkins",
        indexes = {
                @Index(name = "idx_checkins_quest_occurredOn", columnList = "quest_id, occurredOn")
        }
)
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Quest quest;

    @NotNull
    @Column(nullable = false)
    private LocalDate occurredOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Mood mood = Mood.OK;

    @Size(max = 500)
    @Column(length = 500)
    private String note;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public Checkin() {}

    public Checkin(Quest quest, LocalDate occurredOn, Mood mood, String note) {
        this.quest = quest;
        this.occurredOn = occurredOn;
        if (mood != null) this.mood = mood;
        this.note = note;
    }

    public Long getId() { return id; }
    public Quest getQuest() { return quest; }
    public void setQuest(Quest quest) { this.quest = quest; }

    public LocalDate getOccurredOn() { return occurredOn; }
    public void setOccurredOn(LocalDate occurredOn) { this.occurredOn = occurredOn; }

    public Mood getMood() { return mood; }
    public void setMood(Mood mood) { this.mood = mood; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Instant getCreatedAt() { return createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Checkin checkin)) return false;
        return id != null && Objects.equals(id, checkin.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
