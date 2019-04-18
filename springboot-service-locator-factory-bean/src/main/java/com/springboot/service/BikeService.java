package com.springboot.service;

import org.springframework.stereotype.Service;

import com.springboot.registry.AdapterService;

@Service("Bike")
public class BikeService implements AdapterService {
	int numberExecution=1;
	@Override
	public String process() {
		return "inside bike service  number of executions: "+(numberExecution++);
	}
	
}
