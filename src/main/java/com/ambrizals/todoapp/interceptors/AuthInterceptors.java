package com.ambrizals.todoapp.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptors implements HandlerInterceptor {
	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) {
		System.out.println(request.getHeader("Host"));
		System.out.println(request.getRequestURI());
		System.out.println(request.getMethod());
		return true;
	}
}
