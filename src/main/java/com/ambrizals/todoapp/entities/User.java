package com.ambrizals.todoapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.ambrizals.todoapp.utils.HashUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
public class User {

	@PrePersist
	@PreUpdate
	void generatePassword() {
		this.password = HashUtils.generateHash(this.password);
	}

	public static User fromJson(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User user = mapper.readValue(json, User.class);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public User safeOutput() {
		this.password = "SECRET";
		return this;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String username;
	
	// @JsonIgnore
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String fullname;

	public User () {}

	public User (Long id, String username, String fullname, String password) {
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
}
