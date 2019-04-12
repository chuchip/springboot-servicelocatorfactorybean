package com.springboot.service;

import org.springframework.stereotype.Service;

import com.springboot.model.Vehicle;
import com.springboot.registry.AdapterService;

@Service("Bus")
public class BusService implements AdapterService<Vehicle> {

	@Override
	public String process(Vehicle request) {
		return  "inside bus service - " + request.toString();
	}
}
