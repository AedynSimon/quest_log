package com.example.questlog.ui;

import com.example.questlog.api.dto.CheckinRequest;
import com.example.questlog.api.dto.QuestRequest;
import com.example.questlog.domain.Mood;
import com.example.questlog.domain.QuestStatus;
import com.example.questlog.service.CheckinService;
import com.example.questlog.service.InsightsService;
import com.example.questlog.service.QuestService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
public class UiController {

    private final QuestService questService;
    private final CheckinService checkinService;
    private final InsightsService insightsService;

    public UiController(QuestService questService, CheckinService checkinService, InsightsService insightsService) {
        this.questService = questService;
        this.checkinService = checkinService;
        this.insightsService = insightsService;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) QuestStatus status,
            @PageableDefault(size = 20, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        model.addAttribute("page", questService.list(q, status, pageable));
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("status", status);
        model.addAttribute("statuses", QuestStatus.values());
        model.addAttribute("newQuest", new QuestRequest());
        model.addAttribute("streaks", insightsService.streaks());
        return "index";
    }

    @PostMapping("/quests")
    public String createQuest(@Valid @ModelAttribute("newQuest") QuestRequest req, BindingResult br,
                              @RequestParam(required = false) String tagsCsv,
                              Model model) {
        if (tagsCsv != null && !tagsCsv.isBlank()) {
            Set<String> tags = new LinkedHashSet<>();
            Arrays.stream(tagsCsv.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .limit(20)
                    .forEach(tags::add);
            req.tags = tags;
        }

        if (br.hasErrors()) {
            // Re-render index with validation errors
            return index("", null, Pageable.ofSize(20), model);
        }

        questService.create(req);
        return "redirect:/";
    }

    @GetMapping("/quests/{id}")
    public String detail(@PathVariable long id,
                         @PageableDefault(size = 30) Pageable pageable,
                         Model model) {
        model.addAttribute("quest", questService.getOrThrow(id));
        model.addAttribute("checkins", checkinService.list(id, pageable));
        CheckinRequest cr = new CheckinRequest();
        cr.occurredOn = LocalDate.now();
        cr.mood = Mood.OK;
        model.addAttribute("newCheckin", cr);
        model.addAttribute("moods", Mood.values());
        return "quest";
    }

    @PostMapping("/quests/{id}/checkins")
    public String addCheckin(@PathVariable long id,
                             @Valid @ModelAttribute("newCheckin") CheckinRequest req,
                             BindingResult br,
                             Model model) {
        if (br.hasErrors()) {
            return detail(id, Pageable.ofSize(30), model);
        }
        checkinService.upsert(id, req);
        return "redirect:/quests/" + id;
    }

    @PostMapping("/quests/{id}/status")
    public String updateStatus(@PathVariable long id, @RequestParam QuestStatus status) {
        var q = questService.getOrThrow(id);
        QuestRequest req = new QuestRequest();
        req.title = q.getTitle();
        req.description = q.getDescription();
        req.priority = q.getPriority();
        req.status = status;
        req.tags = q.getTags();
        questService.update(id, req);
        return "redirect:/quests/" + id;
    }

    @PostMapping("/quests/{id}/delete")
    public String delete(@PathVariable long id) {
        questService.delete(id);
        return "redirect:/";
    }
}
