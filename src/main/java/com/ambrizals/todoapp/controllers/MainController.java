package com.ambrizals.todoapp.controllers;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MainController {
	
	Object requestBody;
	
	/**
	 * Create request body on response JSON
	 * 
	 * @param Object requestBody
	 */
	public void sendRequestBody(Object requestBody) {
		this.requestBody = requestBody;
	}
	
	/**
	 * Response Builder
	 * 
	 * @param HttpStatus httpStatus
	 * @param Object responseObj
	 * @param String message
	 * @return
	 */
	private HashMap<String, Object> responseBuilder(HttpStatus httpStatus, Object responseObj, String message) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("status", httpStatus.getReasonPhrase());
		if(this.requestBody != null) {
			map.put("request", this.requestBody);
			this.requestBody = null;
		}		
		if(responseObj != null) {
			map.put("data", responseObj);			
		}
		if(message != null) {
			map.put("message", message);			
		}
		return map;
	}
	
	/**
	 * Return response of object and string messages
	 * 
	 * @param String message
	 * @param HttpStatus httpStatus
	 * @param Object responseObj
	 * @return ResponseEntity<Object>
	 */
	public ResponseEntity<Object> fullResponse(String message, HttpStatus httpStatus, Object responseObj) {
		HashMap<String, Object> map = responseBuilder(httpStatus, responseObj, message);		
		return new ResponseEntity<Object>(map, httpStatus);
	}
	
	/**
	 * Return response only return object of entities or other object
	 * 
	 * @param HttpStatus httpStatus
	 * @param Object responseObj
	 * @return ResponseEntity<Object>
	 */
	
	public ResponseEntity<Object> objectResponse(HttpStatus httpStatus, Object responseObj) {
		HashMap<String, Object> map = responseBuilder(httpStatus, responseObj, null);
		return new ResponseEntity<Object>(map, httpStatus);
	}
	
	/**
	 * Return response only return string messages
	 * 
	 * @param HttpStatus httpStatus
	 * @param String message
	 * @return ResponseEntity<Object>
	 */
	public ResponseEntity<Object> messageResponse(HttpStatus httpStatus, String message) {
		HashMap<String, Object> map = responseBuilder(httpStatus, null, message);		
		return new ResponseEntity<Object>(map, httpStatus);
	}	
	
	
	
}
