package com.ambrizals.todoapp.config;

import java.util.ArrayList;
import java.util.List;

public class AppRoutes {
	public static List<Route> listRoutes() {
		List<Route> routes = new ArrayList<Route>();
		
		routes.add(new Route("/v1/auth", "POST", null));
		
		return routes;
	}
}
