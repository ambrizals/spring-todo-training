package com.ambrizals.todoapp.DTO.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateTokenAuthDTO {

  @NotNull(message = "Username tidak boleh kosong")
  @NotBlank(message = "Username tidak boleh kosong")
  private String username;

  @NotNull(message = "Password tidak boleh kosong")
  @NotBlank(message = "Password tidak boleh kosong")
  private String password;

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String toJSON() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(this);
  }  

}
