package com.ambrizals.todoapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
public class Task {
  
  @Id
  private long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;


  @Column
  private Boolean isFinish;
  
  public static Task create(long id, String title, String description, Boolean isFinish) {
    Task task = new Task();
    task.id = id;
    task.title = title;
    task.description = description;
    task.isFinish = isFinish;
    return task;
  }


  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean isIsFinish() {
    return this.isFinish;
  }

  public Boolean getIsFinish() {
    return this.isFinish;
  }

  public void setIsFinish(Boolean isFinish) {
    this.isFinish = isFinish;
  }

  public String toJSON() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(this);
  }

}
