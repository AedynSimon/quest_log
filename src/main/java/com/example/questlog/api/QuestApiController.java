package com.example.questlog.api;

import com.example.questlog.api.dto.QuestRequest;
import com.example.questlog.api.dto.QuestResponse;
import com.example.questlog.api.mapper.QuestMapper;
import com.example.questlog.domain.Quest;
import com.example.questlog.domain.QuestStatus;
import com.example.questlog.service.QuestService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quests")
public class QuestApiController {

    private final QuestService questService;

    public QuestApiController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping
    public Page<QuestResponse> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) QuestStatus status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return questService.list(q, status, pageable).map(QuestMapper::toResponse);
    }

    @GetMapping("/{id}")
    public QuestResponse get(@PathVariable long id) {
        return QuestMapper.toResponse(questService.getOrThrow(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestResponse create(@Valid @RequestBody QuestRequest req) {
        Quest saved = questService.create(req);
        return QuestMapper.toResponse(saved);
    }

    @PutMapping("/{id}")
    public QuestResponse update(@PathVariable long id, @Valid @RequestBody QuestRequest req) {
        return QuestMapper.toResponse(questService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        questService.delete(id);
    }
}
