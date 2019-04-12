package com.springboot.model;

import org.springframework.beans.factory.FactoryBean;

public class VehicleFactory  implements FactoryBean<VehicleFactory> {
	   
	    @Override
	    public VehicleFactory getObject() throws Exception {
	        return new VehicleFactory();
	    }
	 
	    @Override
	    public Class<?> getObjectType() {
	        return VehicleFactory.class;
	    }
	 
	    @Override
	    public boolean isSingleton() {
	        return false;
	    }
}
