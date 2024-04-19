package com.example.taskmanager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.taskmanager.dto.TaskDto;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepo;
import com.example.taskmanager.service.TaskService;

class TaskServiceTest {

    @Mock
    private TaskRepo repo;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", "Description 1", null, false));
        tasks.add(new Task(2L, "Task 2", "Description 2", null, true));
        when(repo.findAll()).thenReturn(tasks);

        List<TaskDto> taskDtoList = taskService.getAllTasks();

        assertEquals(2, taskDtoList.size());
        assertEquals("Task 1", taskDtoList.get(0).getTitle());
        assertEquals("Task 2", taskDtoList.get(1).getTitle());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task(1L, "Task 1", "Description 1", null, false);
        when(repo.findById(1L)).thenReturn(Optional.of(task));

        TaskDto taskDto = taskService.getTaskById(1L);

        assertEquals("Task 1", taskDto.getTitle());
    }

    @Test
    void testEditTask() {
        Task existingTask = new Task(1L, "Existing Task", "Existing Description", null, false);
        TaskDto newDto = new TaskDto();
        newDto.setId(2L);
        newDto.setTitle("Updated Title");
        newDto.setDescription("Updated Description");
        when(repo.findById(1L)).thenReturn(Optional.of(existingTask));

        taskService.editTask(1L, newDto);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(repo).save(captor.capture());
        Task updatedTask = captor.getValue();

        assertEquals(1L, updatedTask.getId());
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Updated Description", updatedTask.getDescription());
    }

    @Test
    void testDeleteTask() {
        Task existingTask = new Task(1L, "Existing Task", "Existing Description", null, false);
        when(repo.findById(1L)).thenReturn(Optional.of(existingTask));

        taskService.deleteTask(1L);

        verify(repo, times(1)).delete(existingTask);
    }
}
