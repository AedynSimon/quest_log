package com.example.questlog.repo;

import com.example.questlog.domain.Quest;
import com.example.questlog.domain.QuestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QuestRepositoryTest {

    @Autowired
    QuestRepository questRepository;

    @Test
    void savesAndQueriesByTitle() {
        Quest q = new Quest("Learn Spring", "Build a small app", QuestStatus.ACTIVE, 4, Set.of("spring", "java"));
        questRepository.save(q);

        var page = questRepository.findByTitleContainingIgnoreCase("spring", org.springframework.data.domain.PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getTitle()).contains("Spring");
    }
}
