package com.cg.bean;

import com.google.common.cache.CacheLoader;

public class PersonCacheLoader extends CacheLoader<String,Person>{

	PersonSerializer personSerializer = new PersonSerializer();

	public Person load(String key) throws Exception {
		return personSerializer.deserialize(key);
	}

	Person person = new Person();

	@Override
	public String toString() {
		return person.toString();
	}	
}