package com.ambrizals.todoapp.controllers;

import java.util.List;
import java.util.Optional;

import com.ambrizals.todoapp.entities.Task;
import com.ambrizals.todoapp.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tasks")
public class TodoController extends MainController {
    
  @Autowired
  private TaskRepository repository;
  
  @GetMapping(value = "")
  public ResponseEntity<Object> getTask() {
    List<Task> tasks = repository.findAll();
    return this.objectResponse(HttpStatus.OK, tasks);
  }

  @Secured({"ROLE_USER"})
  @PostMapping(value = "")
  public ResponseEntity<Object> storeTask(@RequestBody Task request) {
	  this.sendRequestBody(request);
	  repository.save(request);
	  return this.messageResponse(HttpStatus.ACCEPTED, "Berhasil Ditambah !");
  }

  @Secured({"ROLE_USER"})
  @GetMapping(value = "{id}")
  public ResponseEntity<Object> detailTask(@PathVariable("id") long id) {
    Optional<Task> task = repository.findById(id);
    if(task.isPresent()) {
    	return this.objectResponse(HttpStatus.OK, task.get());
    } else {
    	return this.messageResponse(HttpStatus.NOT_FOUND, "Data tidak ditemukan !");
    }
  }

  @Secured({"ROLE_USER"})
  @PutMapping(value = "{id}/finish")
  public ResponseEntity<Object> updateTask(@PathVariable("id") long id)  {
    Optional<Task> task = repository.findById(id);
    if(task.isPresent()) {
      Task current = task.get();
      current.setIsFinish(true);
      repository.save(current);
      return this.messageResponse(HttpStatus.OK, "Data sudah di update !");
    } else {
    	return this.messageResponse(HttpStatus.NOT_FOUND, "Data tidak ditemukan !");
    }
  }
}
