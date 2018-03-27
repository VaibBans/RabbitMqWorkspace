package com.example.demo;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

@Component
public class PersonCache {

	LoadingCache<String, Person> cache;

	@Autowired
	static
	PersonCacheLoader loader;

	@Autowired
	static
	PersonRemovalListener listener;

	/*public LoadingCache<String, Person> init() {
		cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(10, TimeUnit.SECONDS)
				.removalListener(listener).build(loader);
		return cache;
	}*/

	public Person get(String key) throws ExecutionException {
		return cache.get(key);
	}
	public static void main(String[] args) throws ExecutionException{
		Person person = new Person();
		person.setName("Google");
		person.setSurname("Guava");
		person.setUsername("Gooava");
		person.setAge(20);

		System.out.println("1");
		LoadingCache<String, Person> map;
		map = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(10, TimeUnit.SECONDS)
				.removalListener(listener).build(loader);
		System.out.println("2");
		map.put("1", person);
		System.out.println(map.get("1"));
	}
}
