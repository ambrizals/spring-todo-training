package com.ambrizals.todoapp.repositories;

import java.util.List;

import com.ambrizals.todoapp.entities.Task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findAll();
  
  Task findByTitle(String title);
}
