package br.com.alura.forum.controller;

import java.util.List;

import br.com.alura.forum.dto.DashboardDto;
import br.com.alura.forum.dto.TopicSearchInputDto;
import br.com.alura.forum.repository.CategoryRepository;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.service.ServiceDashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.alura.forum.dto.TopicBriefOutputDto;
import br.com.alura.forum.model.topic.domain.Topic;

@RestController
@RequestMapping(value = "/api/topics")
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ServiceDashboard serviceDashboard;

    @GetMapping
    public Page<TopicBriefOutputDto> listTopics(TopicSearchInputDto	topicSearch, @PageableDefault(sort="creationInstant", direction= Sort.Direction.DESC) Pageable pageRequest) {
        Specification<Topic> topicSearchSpecification	=	topicSearch.build();
        Page<Topic>	topics = this.topicRepository.findAll(topicSearchSpecification, pageRequest);
        return	TopicBriefOutputDto.listFromTopics(topics);
    }

    @GetMapping("/dashboard")
    public List<DashboardDto> dashboard(){
        return serviceDashboard.retList(categoryRepository.findByCategoryIsNull());
    }
}