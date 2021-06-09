package com.ambrizals.todoapp.config;

public class Route {
	private String uri;
	private String method;
	private Void middleware;
	
	public Void getMiddleware() {
		return middleware;
	}

	public void setMiddleware(Void middleware) {
		this.middleware = middleware;
	}

	public Route(String uri, String method, Void middleware) {
		this.uri = uri;
		this.method = method;
		this.middleware = middleware;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
}
