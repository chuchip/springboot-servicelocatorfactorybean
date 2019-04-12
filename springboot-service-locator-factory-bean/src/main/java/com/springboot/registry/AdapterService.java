package com.springboot.registry;

public interface AdapterService<T> {
	public String process(T request);
}