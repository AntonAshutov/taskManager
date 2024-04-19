package com.example.taskmanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime dueDate;

    private Boolean completed;

    public void copyNonNullFields(TaskDto other) {
        if (other.getTitle() != null) {
            this.title = other.getTitle();
        }
        if (other.getDescription() != null) {
            this.description = other.getDescription();
        }
        if (other.getDueDate() != null) {
            this.dueDate = other.getDueDate();
        }
        if (other.getCompleted() != null) {
            this.completed = other.getCompleted();
        }
    }
}
