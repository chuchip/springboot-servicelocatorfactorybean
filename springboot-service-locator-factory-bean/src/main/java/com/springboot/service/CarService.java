package com.springboot.service;

import org.springframework.stereotype.Service;

import com.springboot.model.Vehicle;
import com.springboot.registry.AdapterService;

@Service("Car")
public class CarService implements AdapterService<Vehicle> {
	int numeroEjecuciones=1;
	
	@Override
	public String process(Vehicle request) {		
		return "inside car service - " + request.toString()+ " Numero Ejecuciones: "+(numeroEjecuciones++);
	}
}
