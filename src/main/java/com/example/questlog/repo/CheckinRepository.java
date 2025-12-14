package com.example.questlog.repo;

import com.example.questlog.domain.Checkin;
import com.example.questlog.domain.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CheckinRepository extends JpaRepository<Checkin, Long> {
    Page<Checkin> findByQuestOrderByOccurredOnDesc(Quest quest, Pageable pageable);
    Optional<Checkin> findByQuestAndOccurredOn(Quest quest, LocalDate occurredOn);
    List<Checkin> findByQuestOrderByOccurredOnDesc(Quest quest);
}
