package com.ambrizals.todoapp.controllers;

import com.ambrizals.todoapp.DTO.auth.CreateTokenAuthDTO;
import com.ambrizals.todoapp.entities.User;
import com.ambrizals.todoapp.repositories.UserRepository;
import com.ambrizals.todoapp.utils.HashUtils;
import com.ambrizals.todoapp.utils.JwtUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserControllerTest {  
  public MockMvc mockMvc;
  public String token;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private WebApplicationContext wac;
  
  @MockBean
  public UserRepository repository;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac)
      .apply(springSecurity())
      .build();
    token = jwtUtils.generateToken(new User(
      1L, "user", "user", HashUtils.generateHash("password")
    ));
  }

  public String userTestPassword = "siapsiapgas";


  public List<User> multipleData() {
    List<User> users = new ArrayList<>();
    users.add(new User(
      1L, "user", "user", HashUtils.generateHash(userTestPassword)
    ));
    users.add(new User(
      2L, "user-yes", "user-yes", HashUtils.generateHash(userTestPassword)
    ));    
    return users;
  }

  public User singleData() {
    return new User(
      1L, "user", "user", HashUtils.generateHash(userTestPassword)
    );
  }

  @Test
  @WithAnonymousUser
  public void createToken() throws Exception {
    User data = singleData();
    CreateTokenAuthDTO dto = new CreateTokenAuthDTO();
    dto.setUsername(data.getUsername());
    dto.setPassword(userTestPassword);

    when(
      repository.findByUsername(anyString())
    ).thenReturn(data);

    // Check a result
    mockMvc.perform(
      post("/v1/user/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(dto.toJSON())
    )
      .andExpect(status().is(HttpStatus.ACCEPTED.value()));
  }

  @Test
  public void testAuthUser() throws Exception {
    mockMvc.perform(
      get("/v1/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
    ).andExpect(status().is(HttpStatus.OK.value()));
  }
}
