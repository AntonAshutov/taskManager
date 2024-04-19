package com.example.taskmanager;

import com.example.taskmanager.controller.AdviceController;
import com.example.taskmanager.controller.TaskController;
import com.example.taskmanager.dto.TaskDto;
import com.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @InjectMocks
    private AdviceController adviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);    }

    @Test
    void testGet() {
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto());
        tasks.add(new TaskDto());
        when(taskService.getAllTasks()).thenReturn(tasks);

        ResponseEntity<List<TaskDto>> responseEntity = taskController.get();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void testGetById() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        when(taskService.getTaskById(1L)).thenReturn(taskDto);

        ResponseEntity<TaskDto> responseEntity = taskController.getById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getId());
    }

    @Test
    void testEditTask() {
        TaskDto taskDto = new TaskDto();
        doNothing().when(taskService).editTask(1L, taskDto);

        ResponseEntity<Void> responseEntity = taskController.editTask(1L, taskDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }


    @Test
    void testDeleteTask() {
        doNothing().when(taskService).deleteTask(1L);

        ResponseEntity<Void> responseEntity = taskController.deleteTask(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testNewTask() {
        TaskDto taskDto = new TaskDto();
        doNothing().when(taskService).newTask(taskDto);

        ResponseEntity<Void> responseEntity = taskController.newTask(taskDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
