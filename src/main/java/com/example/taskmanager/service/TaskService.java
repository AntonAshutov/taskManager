package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskDto;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepo repo;


    public List<TaskDto> getAllTasks() {
        List<Task> tasks = repo.findAll();
        List<TaskDto> dtoList = new ArrayList<>();
        for (Task task : tasks) {
            dtoList.add(entityToDto(task));
        }
        return dtoList;
    }

    public TaskDto getTaskById(Long id) {
        return entityToDto(repo.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public Void newTask(TaskDto taskDto) {
        repo.save(dtoToEntity(taskDto));
        return null;
    }

    public Void editTask(Long id, TaskDto newDto) {
        Task task = repo.findById(id).orElseThrow(EntityNotFoundException::new);
        TaskDto old = entityToDto(task);
        old.copyNonNullFields(newDto);
        Task updated = dtoToEntity(old);
        updated.setId(old.getId());
        repo.save(updated);
        return null;
    }

    public Void deleteTask(Long id) {
        repo.delete(repo.findById(id).orElseThrow(EntityNotFoundException::new));
        return null;
    }

    private TaskDto entityToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setCompleted(task.getCompleted());
        return dto;
    }

    private Task dtoToEntity(TaskDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setCompleted(dto.getCompleted());
        return task;
    }
}
