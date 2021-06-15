package com.ambrizals.todoapp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HashUtilsTest {
  private String password = "PasswordSaya";
  
  @Test
  public void createHash() {
    String hash = HashUtils.generateHash(password);
    assertNotNull(hash);
  }
  
  @Test
  public void createAndVerifyHash() {
    String hash = HashUtils.generateHash(password);
    boolean verify = HashUtils.verifyHash(password, hash);
    assertEquals(true, verify);
  }

  @Test
  public void createAndVerifyIsNotValidHash() {
    String hash = HashUtils.generateHash(password);
    boolean verify = HashUtils.verifyHash("password", hash);
    assertEquals(false, verify);
  }  
}
