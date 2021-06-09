package com.ambrizals.todoapp.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashUtils {
  private static String SECRET_KEY = "wow";

  public static String generateHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(SECRET_KEY));
  }

  public static boolean verifyHash(String password, String hashed) {
    return BCrypt.checkpw(password, hashed);
  }
}
