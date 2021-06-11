package com.ambrizals.todoapp.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashUtils {
  public static String generateHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(5));
  }

  public static boolean verifyHash(String password, String hashed) {
    return BCrypt.checkpw(password, hashed);
  }
}
