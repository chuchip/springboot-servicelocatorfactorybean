package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.registry.ServiceRegistry;

@RestController
public class VehicleController {

	@Autowired
	private ServiceRegistry serviceRegistry;

	
	@GetMapping("{vehicle}")
	public String  processGet(@PathVariable String vehicle) {
		return serviceRegistry.getService(vehicle).process();
	}
}