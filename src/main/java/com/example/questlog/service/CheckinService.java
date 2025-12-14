package com.example.questlog.service;

import com.example.questlog.api.dto.CheckinRequest;
import com.example.questlog.domain.Checkin;
import com.example.questlog.domain.Quest;
import com.example.questlog.repo.CheckinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckinService {

    private final CheckinRepository checkinRepository;
    private final QuestService questService;

    public CheckinService(CheckinRepository checkinRepository, QuestService questService) {
        this.checkinRepository = checkinRepository;
        this.questService = questService;
    }

    @Transactional
    public Checkin upsert(long questId, CheckinRequest req) {
        Quest quest = questService.getOrThrow(questId);

        return checkinRepository.findByQuestAndOccurredOn(quest, req.occurredOn)
                .map(existing -> {
                    existing.setMood(req.mood);
                    existing.setNote(req.note);
                    return checkinRepository.save(existing);
                })
                .orElseGet(() -> checkinRepository.save(new Checkin(quest, req.occurredOn, req.mood, req.note)));
    }

    @Transactional(readOnly = true)
    public Page<Checkin> list(long questId, Pageable pageable) {
        Quest quest = questService.getOrThrow(questId);
        return checkinRepository.findByQuestOrderByOccurredOnDesc(quest, pageable);
    }
}
