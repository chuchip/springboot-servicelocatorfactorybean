package com.springboot.registry;

public interface ServiceRegistry {
	public AdapterService getService(String serviceName);
}
