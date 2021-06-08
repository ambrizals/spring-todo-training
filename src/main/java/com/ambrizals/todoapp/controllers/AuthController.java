package com.ambrizals.todoapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
public class AuthController extends MainController {
	
	@GetMapping(value = "")
	public ResponseEntity<Object> checkAuth() {
		return this.fullResponse("Berhasil", HttpStatus.OK, "Siap");
	}
	
}
