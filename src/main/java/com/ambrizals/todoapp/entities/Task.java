package com.ambrizals.todoapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

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
  
  public Task(String title, String description, Boolean isFinish) {
    this.title = title;
    this.description = description;
    this.isFinish = isFinish;
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

}
