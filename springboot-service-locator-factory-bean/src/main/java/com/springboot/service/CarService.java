package com.springboot.service;

import org.springframework.stereotype.Service;

import com.springboot.registry.AdapterService;

@Service("Car")
public class CarService implements AdapterService {
	int numberExecution=1;
	
	@Override
	public String process() {		
		return "inside car service -  number of executions: "+(numberExecution++);
	}
}
