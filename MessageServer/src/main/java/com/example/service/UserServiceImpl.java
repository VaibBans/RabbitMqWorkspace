package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dao.UserDao;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao dao;
//	LoadingCache<String, Person> cache ;

	/*@Autowired
	static
	PersonCacheLoader loader;*/

	/*
	 * @Autowired static PersonRemovalListener listener;
	 * 
	 * public LoadingCache<String, Person> init() { cache =
	 * CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(10,
	 * TimeUnit.SECONDS) .removalListener(listener).build(loader); return cache;
	 * }
	 */

	@Override
	public boolean validateUsername(String userName) {
		return dao.validateUsername(userName);
	}

	@Override
	public boolean validatePassword(String password) {
		return dao.validatePassword(password);
	}

	@Override
	public void sendMessage(String message) throws Exception {

	}

	@Override
	public String receiveMessage() throws Exception {
		return dao.receiveMessage();
	}

	/*@Override
	public String getMessageFromCache() throws Exception {
		// TODO Auto-generated method stub
		//		getfromcache
		//		cache.getke("message")
		//		key,List<String> use list of strings
		return cache.get("1").getName();
	}

	@Override
	public void putMessage(String messageToCache) {
		// TODO Auto-generated method stub

		//		push this back to cache
		key=>messages
		 * value=ListofString
		 * 
		cache=init();
		Person p1=new Person();
		p1.setName(messageToCache);
		cache.put("1", p1);
	}*/

}
