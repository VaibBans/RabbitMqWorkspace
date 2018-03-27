// <copyright file="PersonCacheLoader.java">
// Copyright (c) 2012 All Rights Reserved, yusufaytas.com
// <author>Yusuf Aytas</author>
// </copyright>
package com.yusufaytas.examples.guava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheLoader;

@Component
public class PersonCacheLoader extends CacheLoader<String,Person>{
	
	@Autowired
	PersonSerializer personSerializer;
	
	public Person load(String key) throws Exception {
		return personSerializer.deserialize(key);
	}
}
