package com.code.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.code.backend.model.Task;
import com.code.backend.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody @Validated Task task) {
		Task savedTask = taskService.createTask(task);
		return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
	}

	@GetMapping
	public List<Task> getTasks() {
		return taskService.getTasks();
	}

	@GetMapping("/filter")
	public List<Task> filterTasks(@RequestParam int due) {
		return taskService.getTasks(due);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Task> getTask(@PathVariable long id) {
		Task emp = taskService.getTask(id);
		return ResponseEntity.ok(emp);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
		Task emp = taskService.updateTask(task, id);
		return ResponseEntity.ok(emp);
	}

	@PutMapping("/{id}/complete")
	public ResponseEntity<Task> completeTask(@PathVariable("id") long id) {
		Task emp = taskService.completeTask(id);
		return ResponseEntity.ok(emp);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteTask(@PathVariable("id") long id) {
		taskService.deleteTask(id);
		return ResponseEntity.ok("Task deleted successfully");
	}

}