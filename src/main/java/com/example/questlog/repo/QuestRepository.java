package com.example.questlog.repo;

import com.example.questlog.domain.Quest;
import com.example.questlog.domain.QuestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    Page<Quest> findByStatusAndTitleContainingIgnoreCase(QuestStatus status, String q, Pageable pageable);
    Page<Quest> findByTitleContainingIgnoreCase(String q, Pageable pageable);
    Page<Quest> findByStatus(QuestStatus status, Pageable pageable);
}
