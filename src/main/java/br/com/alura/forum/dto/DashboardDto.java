package br.com.alura.forum.dto;

import br.com.alura.forum.model.Category;
import br.com.alura.forum.repository.TopicRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardDto {
    private String categoryName;
    private List<String> subcategories;
    private int allTopics;
    private int lastWeekTopics;
    private int unansweredTopics;

    public DashboardDto(Category category, int allTopics, int lastWeekTopics, int unansweredTopics){
        this.categoryName = category.getName();
        this.subcategories = category.getSubcategoryNames();
        this.allTopics = allTopics;
        this.lastWeekTopics = lastWeekTopics;
        this.unansweredTopics = unansweredTopics;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<String> subcategories) {
        this.subcategories = subcategories;
    }

    public int getAllTopics() {
        return allTopics;
    }

    public void setAllTopics(int allTopics) {
        this.allTopics = allTopics;
    }

    public int getLastWeekTopics() {
        return lastWeekTopics;
    }

    public void setLastWeekTopics(int lastWeekTopics) {
        this.lastWeekTopics = lastWeekTopics;
    }

    public int getUnansweredTopics() {
        return unansweredTopics;
    }

    public void setUnansweredTopics(int unansweredTopics) {
        this.unansweredTopics = unansweredTopics;
    }
}
