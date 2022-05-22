package com.code.backend.service;

import java.time.LocalDate;
import java.util.List;

import com.code.backend.common.TaskStatus;
import org.springframework.stereotype.Service;

import com.code.backend.exception.ResourceNotFoundException;
import com.code.backend.model.Task;
import com.code.backend.repository.TaskRepo;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Task createTask(Task task) {
        return taskRepo.save(task);
    }

    public List<Task> getTasks() {
        return taskRepo.findAll();
    }

    public List<Task> getTasks(int due) {
        LocalDate now = LocalDate.now();
        if (due == 0 || due == 1) {
            // This is to fetch today and tomorrow due tasks
            return taskRepo.findByDueDate(now.plusDays(due));
        } else if (due == 2) {
            return taskRepo.findByDueDateLessThan(now);
        }
        return getTasks();
    }

    public Task getTask(long id) {
        return taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
    }

    public Task updateTask(Task task, long id) {
        Task existingTask = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        taskRepo.save(existingTask);
        return existingTask;
    }

    public Task completeTask(long id) {
        Task existingTask = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
        existingTask.setStatus(TaskStatus.COMPLETED);
        taskRepo.save(existingTask);
        return existingTask;
    }

    public void deleteTask(long id) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
        taskRepo.delete(task);
    }

}