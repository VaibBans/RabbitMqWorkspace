package com.cg.bean;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;

public class PersonCache {

	LoadingCache<String, Person> cache;

	PersonCacheLoader loader = new PersonCacheLoader();

	PersonRemovalListener listener = new PersonRemovalListener();

	public void init() {
		cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(10, TimeUnit.SECONDS)
				.removalListener(listener).build(loader);
	}

	public Person get(String key) throws ExecutionException {
		return cache.get(key);
	}
	
	public static void main (String[] args) throws Exception{
		System.out.println("Above instantiation");
		//PersonCache personCache = null;
		System.out.println("After");
		LoadingCache<String, Person> cache = new LoadingCache<String, Person>() {
			
			@Override
			public CacheStats stats() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long size() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void putAll(Map<? extends String, ? extends Person> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void put(String arg0, Person arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void invalidateAll(Iterable<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void invalidateAll() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void invalidate(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Person getIfPresent(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ImmutableMap<String, Person> getAllPresent(Iterable<?> arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Person get(String arg0, Callable<? extends Person> arg1) throws ExecutionException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void cleanUp() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void refresh(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Person getUnchecked(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ImmutableMap<String, Person> getAll(Iterable<? extends String> arg0) throws ExecutionException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Person get(String arg0) throws ExecutionException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ConcurrentMap<String, Person> asMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Person apply(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		PersonCacheLoader personCacheLoader = new PersonCacheLoader();
//		personCache.init();
		System.out.println("After init");
		Person person = new Person();
		person.setName("Google");
		person.setSurname("Guava");
		person.setUsername("gooava");
		person.setAge(20);
		cache.put("1", person);
		System.out.println(person.toString());
		personCacheLoader.load("1");
		System.out.println(personCacheLoader.toString());
		if(cache.size()==0)
			System.out.println("No values");
		else
		System.out.println(cache.get("1"));
	}
}
