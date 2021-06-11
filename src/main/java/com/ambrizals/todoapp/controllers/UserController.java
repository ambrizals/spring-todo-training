package com.ambrizals.todoapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.ambrizals.todoapp.DTO.auth.CreateTokenAuthDTO;
import com.ambrizals.todoapp.entities.User;
import com.ambrizals.todoapp.repositories.UserRepository;
import com.ambrizals.todoapp.utils.HashUtils;
import com.ambrizals.todoapp.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("v1/user")
public class UserController extends MainController {

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserRepository repository;
  
  @Secured({"ROLE_USER"})
  @PostMapping(value="")
  public ResponseEntity<Object> createData(@RequestBody User request) {
      User user = new User();
      user.setUsername(request.getUsername());
      user.setPassword(request.getPassword());
      user.setFullname(request.getFullname());

      repository.save(user);

      this.sendRequestBody(request);
      return this.messageResponse(HttpStatus.ACCEPTED, "User berhasil di tambah !");
  }

  @Secured({"ROLE_ANONYMOUS"})
  @PostMapping(value = "create")  
  public ResponseEntity<Object> createToken(@RequestBody @Valid CreateTokenAuthDTO request) {
    User user = repository.findByUsername(request.getUsername());

    if (HashUtils.verifyHash(request.getPassword(), user.getPassword())) {

      String token = jwtUtils.generateToken(user);

      this.sendRequestBody(request);
      return this.fullResponse("Login Berhasil !", HttpStatus.ACCEPTED, token);


    } else {
      this.sendRequestBody(request);
      return this.messageResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Password Salah");
    }
  }
  

}
