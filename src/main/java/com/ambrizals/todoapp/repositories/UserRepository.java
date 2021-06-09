package com.ambrizals.todoapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambrizals.todoapp.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findAll();
	User findByUsername(String username);
}
