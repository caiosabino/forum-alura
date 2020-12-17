package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import br.com.alura.forum.dto.*;
import br.com.alura.forum.model.User;
import br.com.alura.forum.repository.CategoryRepository;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.service.ServiceDashboard;
import br.com.alura.forum.validator.dto.NewTopicCustomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import br.com.alura.forum.model.topic.domain.Topic;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/topics")
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ServiceDashboard serviceDashboard;

    @Autowired
    private CourseRepository courseRepository;

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopicOutputDto> createTopic(
            @Valid @RequestBody NewTopicInputDto newTopicDto,
            @AuthenticationPrincipal User loggedUser,
            UriComponentsBuilder uriBuilder){
        Topic topic = newTopicDto.build(loggedUser, this.courseRepository);
        this.topicRepository.save(topic);
        URI path = uriBuilder.path("/api/topics/{id}")
                .buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(path).body(new TopicOutputDto(topic));
    }

    @InitBinder("newTopicInputDto")
    public void initBinder(WebDataBinder binder, @AuthenticationPrincipal User loggedUser) {
        binder.addValidators(new NewTopicCustomValidator(this.topicRepository, loggedUser));
    }
}