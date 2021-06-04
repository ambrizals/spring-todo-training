package com.ambrizals.todoapp.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import com.ambrizals.todoapp.entities.Task;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionalTestExecutionListener.class,
  DbUnitTestExecutionListener.class
})
public class TaskRepositoryTest {
  @Autowired
  TaskRepository repository;

  @Test
  @DatabaseSetup("/dataset/task.xml")
  public void fetchAll() {
    List<Task> task = repository.findAll();
    assertNotNull(task);
  }

  @Test
  @DatabaseSetup("/dataset/task.xml")
  public void fetchSomeTitle() {
    Task task = repository.findByTitle("Task 1");
    assertEquals("Task 1", task.getTitle());
  }

  @Test
  @DatabaseSetup("/dataset/task.xml")
  public void updateSomeData() {
    Task task = repository.findByTitle("Task 2");
    task.setTitle("Task Ku Udah Di Edit");
    task = repository.save(task);

    assertEquals("Task Ku Udah Di Edit", task.getTitle());
  }
}
