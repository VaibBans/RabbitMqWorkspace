package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheLoader;

@Component
public class PersonCacheLoader extends CacheLoader {
	
	@Autowired
	PersonSerializer personSerializer;
	
	public Person load(String key) throws Exception{
	
		return personSerializer.deserialize(key);
	
	
	}

	@Override
	public Object load(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
