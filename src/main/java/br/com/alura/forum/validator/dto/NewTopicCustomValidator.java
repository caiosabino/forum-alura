package br.com.alura.forum.validator.dto;

import br.com.alura.forum.dto.NewTopicInputDto;
import br.com.alura.forum.model.PossibleSpam;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.repository.TopicRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

public class NewTopicCustomValidator implements Validator {
    private final TopicRepository topicRepository;
    private User loggedUser;

    public NewTopicCustomValidator(TopicRepository topicRepository, User loggedUser) {
        this.topicRepository = topicRepository;
        this.loggedUser = loggedUser;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewTopicInputDto.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Instant oneHourAgo = Instant.now().minus(1, ChronoUnit.HOURS);
        List<Topic> topics = topicRepository.findByOwnerAndCreationInstantAfterOrderByCreationInstantAsc(loggedUser, oneHourAgo);
        PossibleSpam possibleSpam = new PossibleSpam(topics);
        if (possibleSpam.hasTopicLimitExceeded()) {
            long minutesToNextTopic = possibleSpam.minutesToNextTopic(oneHourAgo);
            errors.reject("newTopicInputDto.limit.exceeded",
                    new Object[]{minutesToNextTopic},
                    "O limite individual de novos tópicos por hora foi excedido");
        }
    }
}
