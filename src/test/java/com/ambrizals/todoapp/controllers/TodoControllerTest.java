package com.ambrizals.todoapp.controllers;

import com.ambrizals.todoapp.entities.Task;
import com.ambrizals.todoapp.repositories.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TodoControllerTest {
  public MockMvc mockMvc;

  @MockBean
  public TaskRepository repository;

  @Autowired
  public TodoController controller;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }
  
  /**
   * Single Data Mock
   * 
   * @return Task
   */
  public Optional<Task> singleData() {
    Optional<Task> task = Optional.of(Task.create(1L, "title", "description", true));
    return task;
  }
  
  /**
   * Multiple Data Mock
   * 
   * 
   * @return List<Task>
   */
  public List<Task> multipleData() {
	  List<Task> tasks = new ArrayList<Task>();
	  tasks.add(Task.create(1L, "title 1", "description", false));
	  tasks.add(Task.create(2L, "title 2", "description", true));
	  return tasks;
  }

  @Test
  public void testGetTask() throws Exception {
    when(repository.findAll())
      .thenReturn(multipleData());

    // Check a result from controller
    mockMvc.perform(get("/v1/tasks"))
      .andExpect(status().is(HttpStatus.OK.value()))
      .andExpect(jsonPath("$.data.[0].title").value("title 1"))
      .andReturn();
  }

  @Test
  public void testStoreTask() throws Exception {
    // Create simulation data
    Task expectedData = Task.create(1L, "Contoh Title", "Contoh Deskripsi", false);
    Task simulatedStore = new Task();
    simulatedStore.setTitle(expectedData.getTitle());
    simulatedStore.setDescription(expectedData.getDescription());
    
    when(repository.save(any())).thenReturn(expectedData);

    // Check a result
    mockMvc.perform(
      post("/v1/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(simulatedStore.toJSON().toString())
    )
      .andExpect(status().is(HttpStatus.ACCEPTED.value()))
      .andReturn();
  }

  @Test
  public void testDetailTask() throws Exception {
    // Create a mock data
    when(repository.findById(1L))
      .thenReturn(singleData());

    // Check result from controller
    mockMvc.perform(get("/v1/tasks/1"))
      .andExpect(status().is(HttpStatus.OK.value()))
      .andReturn();
  }

  @Test
  public void testDetailWhenNotFound() throws Exception {
    // Check result from controller
    mockMvc.perform(get("/v1/tasks/1"))
      .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
      .andReturn();
  }  

  @Test
  public void updateTask() throws Exception {
    // Create a mock data
    when(repository.findById(1L))
      .thenReturn(singleData());

    // Check result from controller
    mockMvc.perform(put("/v1/tasks/1/finish"))
      .andExpect(status().is(HttpStatus.OK.value()))
      .andReturn();
  }
  
  @Test
  public void notUpdateTaskWhenNotFound() throws Exception {
    // Check result from controller
    mockMvc.perform(put("/v1/tasks/1/finish"))
      .andExpect(status().is(HttpStatus.NOT_FOUND.value()	))
      .andReturn();
  }
}
