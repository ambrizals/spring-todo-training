package com.ambrizals.todoapp.exceptions;

public class RequiredFieldException extends Exception {
  
  public RequiredFieldException() {
    super("field ini belum terisi");
  }

}
