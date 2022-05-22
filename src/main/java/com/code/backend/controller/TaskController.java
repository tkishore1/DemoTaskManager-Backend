package com.code.backend.controller;

import java.util.List;

import com.code.backend.model.TaskDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.code.backend.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping
	public ResponseEntity<TaskDto> createTask(@RequestBody @Validated TaskDto task) {
		TaskDto savedTask = taskService.createTask(task);
		return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
	}

	@GetMapping
	public List<TaskDto> getTasks() {
		return taskService.getTasks();
	}

	@GetMapping("/filter")
	public List<TaskDto> filterTasks(@RequestParam int due) {
		return taskService.getTasks(due);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskDto> getTask(@PathVariable long id) {
		TaskDto taskDto = taskService.getTask(id);
		return ResponseEntity.ok(taskDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaskDto> updateTask(@PathVariable("id") long id, @RequestBody TaskDto task) {
		TaskDto taskDto = taskService.updateTask(task, id);
		return ResponseEntity.ok(taskDto);
	}

	@PutMapping("/{id}/complete")
	public ResponseEntity<TaskDto> completeTask(@PathVariable("id") long id) {
		TaskDto taskDto = taskService.completeTask(id);
		return ResponseEntity.ok(taskDto);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteTask(@PathVariable("id") long id) {
		taskService.deleteTask(id);
		return ResponseEntity.ok("Task deleted successfully");
	}

}