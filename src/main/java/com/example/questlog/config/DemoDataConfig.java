package com.example.questlog.config;

import com.example.questlog.domain.Mood;
import com.example.questlog.domain.Quest;
import com.example.questlog.domain.QuestStatus;
import com.example.questlog.repo.CheckinRepository;
import com.example.questlog.repo.QuestRepository;
import com.example.questlog.domain.Checkin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(DemoDataConfig.DemoDataProps.class)
public class DemoDataConfig {

    @Bean
    CommandLineRunner demoData(QuestRepository questRepository, CheckinRepository checkinRepository, DemoDataProps props) {
        return args -> {
            if (!props.demoData) return;
            if (questRepository.count() > 0) return;

            Quest q1 = questRepository.save(new Quest(
                    "Daily movement",
                    "A tiny habit with huge upside. Walk, stretch, or lift — just show up.",
                    QuestStatus.ACTIVE,
                    5,
                    Set.of("health", "consistency")
            ));

            Quest q2 = questRepository.save(new Quest(
                    "Build something small",
                    "Ship a small thing each week. Keep the loop tight.",
                    QuestStatus.ACTIVE,
                    4,
                    Set.of("craft", "shipping")
            ));

            Quest q3 = questRepository.save(new Quest(
                    "Read 20 pages",
                    "Read before bed. One chapter is enough.",
                    QuestStatus.PAUSED,
                    3,
                    Set.of("learning")
            ));

            // Seed a few checkins for streak behavior
            LocalDate today = LocalDate.now();
            for (int i = 0; i < 3; i++) {
                checkinRepository.save(new Checkin(q1, today.minusDays(i), Mood.GREAT, i == 0 ? "Short walk + stretch" : null));
            }
            checkinRepository.save(new Checkin(q2, today.minusDays(1), Mood.OK, "Fixed a bug + wrote tests"));
            checkinRepository.save(new Checkin(q2, today.minusDays(3), Mood.ROUGH, "Tired, still did 20 minutes"));
        };
    }

    @ConfigurationProperties(prefix = "questlog")
    public static class DemoDataProps {
        public boolean demoData = true;
    }
}
