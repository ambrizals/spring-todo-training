package com.ambrizals.todoapp.controllers;

import com.ambrizals.todoapp.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("v1/auth")	
public class AuthController extends MainController {
	
	@Autowired
	JwtUtils jwtUtils;

  @Secured({"ROLE_USER"})
	@GetMapping(value = "")
	public ResponseEntity<Object> checkAuth() {
		Claims data = jwtUtils.extractUserData();
		return this.fullResponse("Berhasil", HttpStatus.OK, data);
	}
	
}
