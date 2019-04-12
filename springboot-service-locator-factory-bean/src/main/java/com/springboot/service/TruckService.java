package com.springboot.service;

import org.springframework.stereotype.Service;

import com.springboot.model.Vehicle;
import com.springboot.registry.AdapterService;

@Service("Truck")
public class TruckService implements AdapterService<Vehicle> {

	@Override
	public String process(Vehicle request) {
		// TODO Auto-generated method stub
		return "inside truck service - " + request.toString();
	}
}
