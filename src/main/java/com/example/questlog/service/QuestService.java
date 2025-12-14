package com.example.questlog.service;

import com.example.questlog.api.dto.QuestRequest;
import com.example.questlog.domain.Quest;
import com.example.questlog.domain.QuestStatus;
import com.example.questlog.repo.QuestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestService {

    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    @Transactional(readOnly = true)
    public Quest getOrThrow(long id) {
        return questRepository.findById(id).orElseThrow(() -> new NotFoundException("Quest", id));
    }

    @Transactional
    public Quest create(QuestRequest req) {
        Quest quest = new Quest(req.title, req.description, req.status, req.priority, req.tags);
        return questRepository.save(quest);
    }

    @Transactional
    public Quest update(long id, QuestRequest req) {
        Quest quest = getOrThrow(id);
        quest.setTitle(req.title);
        quest.setDescription(req.description);
        quest.setStatus(req.status);
        quest.setPriority(req.priority);
        quest.setTags(req.tags);
        return questRepository.save(quest);
    }

    @Transactional
    public void delete(long id) {
        Quest quest = getOrThrow(id);
        questRepository.delete(quest);
    }

    @Transactional(readOnly = true)
    public Page<Quest> list(String q, QuestStatus status, Pageable pageable) {
        String query = (q == null) ? "" : q.trim();
        boolean hasQ = !query.isEmpty();
        boolean hasStatus = status != null;

        if (hasQ && hasStatus) return questRepository.findByStatusAndTitleContainingIgnoreCase(status, query, pageable);
        if (hasQ) return questRepository.findByTitleContainingIgnoreCase(query, pageable);
        if (hasStatus) return questRepository.findByStatus(status, pageable);
        return questRepository.findAll(pageable);
    }
}
