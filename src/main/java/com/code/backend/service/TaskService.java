package com.code.backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.code.backend.common.TaskStatus;
import com.code.backend.model.TaskDto;
import org.springframework.stereotype.Service;

import com.code.backend.exception.ResourceNotFoundException;
import com.code.backend.entity.Task;
import com.code.backend.repository.TaskRepo;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    /**
     * This method is used to create new task
     *
     * @param taskDto - task
     * @return - saved task
     */
    public TaskDto createTask(TaskDto taskDto) {
        Task task = fromTaskDto(taskDto);
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = taskRepo.save(task);
        return toTaskDto(savedTask);
    }

    /**
     * This method is used to get tasks
     *
     * @return - task
     */
    public List<TaskDto> getTasks() {
        return taskRepo.findAll().stream().map(this::toTaskDto).collect(Collectors.toList());
    }

    /**
     * This method is used to fetch today and tomorrow due tasks
     *
     * @param due
     * @return - due task
     */
    public List<TaskDto> getTasks(int due) {
        LocalDate now = LocalDate.now();
        if (due == 0 || due == 1) {
            // This is to fetch today and tomorrow due tasks
            return taskRepo.findByDueDate(now.plusDays(due)).stream().map(this::toTaskDto).collect(Collectors.toList());
        } else if (due == 2) {
            return taskRepo.findByDueDateLessThan(now).stream().map(this::toTaskDto).collect(Collectors.toList());
        }
        return getTasks();
    }

    /**
     * This method is used to get task with id
     *
     * @param id
     * @return - task with id
     */
    public TaskDto getTask(long id) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
        return toTaskDto(task);
    }

    /**
     * This method is used to update task details using id
     *
     * @param task
     * @param id
     * @return - existing task
     */
    public TaskDto updateTask(TaskDto task, long id) {
        Task existingTask = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        taskRepo.save(existingTask);
        return toTaskDto(existingTask);
    }

    /**
     * This method is used to update task status to completed
     *
     * @param id
     * @return - task
     */
    public TaskDto completeTask(long id) {
        Task existingTask = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
        existingTask.setStatus(TaskStatus.COMPLETED);
        taskRepo.save(existingTask);
        return toTaskDto(existingTask);
    }

    /**
     * This method is used to delete the task using task id
     *
     * @param id
     */
    public void deleteTask(long id) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
        taskRepo.delete(task);
    }

    private Task fromTaskDto(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        return task;
    }

    private TaskDto toTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setDueDate(task.getDueDate());
        taskDto.setStatus(task.getStatus().name());
        return taskDto;
    }

}