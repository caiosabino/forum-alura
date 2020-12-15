package br.com.alura.forum.service;

import br.com.alura.forum.dto.DashboardDto;
import br.com.alura.forum.model.Category;
import br.com.alura.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceDashboard {
    @Autowired
    TopicRepository topicRepository;

    public List<DashboardDto> retList(List<Category> categories){
        return categories.stream()
                .map(category -> new DashboardDto(category, topicRepository.countTopicsByCategory(category), topicRepository.countLastWeekTopicsByCategory(category, Instant.now().minus(7, ChronoUnit.DAYS)), topicRepository.countUnansweredTopicsByCategory(category)))
                .collect(Collectors.toList());
    }
}
