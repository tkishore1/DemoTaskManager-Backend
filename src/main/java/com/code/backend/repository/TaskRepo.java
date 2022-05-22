package com.code.backend.repository;

import com.code.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long>{

    List<Task> findByDueDate(LocalDate dueDate);

    List<Task> findByDueDateLessThan(LocalDate dueDate);

}