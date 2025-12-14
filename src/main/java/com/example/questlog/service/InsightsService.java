package com.example.questlog.service;

import com.example.questlog.api.dto.StreakResponse;
import com.example.questlog.domain.Checkin;
import com.example.questlog.domain.Quest;
import com.example.questlog.repo.CheckinRepository;
import com.example.questlog.repo.QuestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class InsightsService {

    private final QuestRepository questRepository;
    private final CheckinRepository checkinRepository;

    public InsightsService(QuestRepository questRepository, CheckinRepository checkinRepository) {
        this.questRepository = questRepository;
        this.checkinRepository = checkinRepository;
    }

    @Transactional(readOnly = true)
    public List<StreakResponse> streaks() {
        List<Quest> quests = questRepository.findAll();
        List<StreakResponse> out = new ArrayList<>();

        for (Quest q : quests) {
            List<Checkin> checkins = checkinRepository.findByQuestOrderByOccurredOnDesc(q);
            Set<LocalDate> days = new HashSet<>();
            for (Checkin c : checkins) days.add(c.getOccurredOn());

            int current = computeCurrentStreak(days);
            int best = computeBestStreak(days);

            StreakResponse r = new StreakResponse();
            r.questId = q.getId();
            r.title = q.getTitle();
            r.currentStreakDays = current;
            r.bestStreakDays = best;
            out.add(r);
        }

        out.sort(Comparator.comparingInt((StreakResponse r) -> r.currentStreakDays).reversed());
        return out;
    }

    static int computeCurrentStreak(Set<LocalDate> days) {
        int streak = 0;
        LocalDate d = LocalDate.now();
        while (days.contains(d)) {
            streak++;
            d = d.minusDays(1);
        }
        return streak;
    }

    static int computeBestStreak(Set<LocalDate> days) {
        if (days.isEmpty()) return 0;
        List<LocalDate> sorted = new ArrayList<>(days);
        Collections.sort(sorted);
        int best = 1;
        int run = 1;
        for (int i = 1; i < sorted.size(); i++) {
            LocalDate prev = sorted.get(i - 1);
            LocalDate cur = sorted.get(i);
            if (cur.equals(prev.plusDays(1))) {
                run++;
                best = Math.max(best, run);
            } else {
                run = 1;
            }
        }
        return best;
    }
}
