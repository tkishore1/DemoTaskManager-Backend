package com.code.backend.service;

import com.code.backend.common.TaskStatus;
import com.code.backend.entity.Task;
import com.code.backend.model.TaskDto;
import com.code.backend.repository.TaskRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepo taskRepo;

    @Test
    void createTask() {
        Task task = randomTask();
        when(taskRepo.save(any())).thenReturn(task);
        TaskDto taskDto = taskService.createTask(new TaskDto());
        assertNotNull(taskDto);
        assertEquals(taskDto.getName(), task.getName());
    }

    @Test
    void getTasks() {
        Task task = randomTask();
        when(taskRepo.findAll()).thenReturn(Collections.singletonList(task));
        List<TaskDto> taskDtos = taskService.getTasks();
        assertNotNull(taskDtos);
        assertFalse(taskDtos.isEmpty());
        assertEquals(1, taskDtos.size());
    }

    @Test
    void testGetTasks() {
        Task task = randomTask();
        when(taskRepo.findByDueDate(any())).thenReturn(Collections.singletonList(task));
        List<TaskDto> taskDtos = taskService.getTasks(1);
        assertNotNull(taskDtos);
        assertFalse(taskDtos.isEmpty());
        assertEquals(1, taskDtos.size());
    }

    @Test
    void testGetTasksOverDue() {
        Task task = randomTask();
        when(taskRepo.findByDueDateLessThan(any())).thenReturn(Collections.singletonList(task));
        List<TaskDto> taskDtos = taskService.getTasks(2);
        assertNotNull(taskDtos);
        assertFalse(taskDtos.isEmpty());
        assertEquals(1, taskDtos.size());
    }

    @Test
    void getTask() {
        Task task = randomTask();
        when(taskRepo.findById(anyLong())).thenReturn(Optional.of(task));
        TaskDto taskDto = taskService.getTask(1L);
        assertNotNull(taskDto);
        assertEquals(taskDto.getName(), task.getName());
    }

    @Test
    void updateTask() {
        Task task = randomTask();
        when(taskRepo.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepo.save(any())).thenReturn(task);
        TaskDto taskDto = taskService.updateTask(new TaskDto(), 1L);
        assertNotNull(taskDto);
        assertEquals(taskDto.getName(), task.getName());
    }

    @Test
    void completeTask() {
        Task task = randomTask();
        when(taskRepo.findById(any())).thenReturn(Optional.of(task));
        when(taskRepo.save(any())).thenReturn(task);
        TaskDto taskDto = taskService.completeTask(1L);
        assertNotNull(taskDto);
        assertEquals(taskDto.getStatus(), TaskStatus.COMPLETED.name());
    }

    @Test
    void deleteTask() {
        Task task = randomTask();
        when(taskRepo.findById(any())).thenReturn(Optional.of(task));
        taskService.deleteTask(1L);
        verify(taskRepo, times(1)).delete(any());
    }

    private Task randomTask() {
        int random = new Random().nextInt();
        Task task = new Task();
        task.setName("Task" + random);
        task.setDescription("Description" + random);
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(LocalDate.now());
        return task;
    }

}