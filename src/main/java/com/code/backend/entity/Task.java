package com.code.backend.entity;

import com.code.backend.common.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	private String description;
	private LocalDate dueDate;
	private TaskStatus status;

}