package com.ambrizals.todoapp.controllers;

import java.util.List;
import java.util.Optional;

import com.ambrizals.todoapp.entities.Task;
import com.ambrizals.todoapp.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tasks")
public class TodoController {
    
  @Autowired
  private TaskRepository repository;

  @GetMapping(value = "")
  public ResponseEntity<List<Task>> getTask() {
    List<Task> tasks = repository.findAll();
    return ResponseEntity.ok(tasks);
  }

  @PostMapping(value = "")
  public ResponseEntity<Task> storeTask(@RequestBody Task request) {
    Task task = repository.save(request);
    return ResponseEntity.ok(task);
  }

  @GetMapping(value = "{id}")
  public ResponseEntity<Task> detailTask(@PathVariable("id") long id) {
    Optional<Task> task = repository.findById(id);
    if(task.isPresent()) {
      return ResponseEntity.ok(task.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(value = "{id}/finish")
  public ResponseEntity<String> updateTask(@PathVariable("id") long id)  {
    Optional<Task> task = repository.findById(id);
    if(task.isPresent()) {
      Task current = task.get();
      current.setIsFinish(true);
      repository.save(current);
      return ResponseEntity.ok("Sudah di update");
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
