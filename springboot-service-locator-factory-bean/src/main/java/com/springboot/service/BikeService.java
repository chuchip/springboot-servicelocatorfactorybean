package com.springboot.service;

import org.springframework.stereotype.Service;

import com.springboot.model.Vehicle;
import com.springboot.registry.AdapterService;

@Service("Bike")
public class BikeService implements AdapterService<Vehicle> {

	@Override
	public String process(Vehicle request) {
		return "inside bike service - " + request.toString();
	}
	
}
