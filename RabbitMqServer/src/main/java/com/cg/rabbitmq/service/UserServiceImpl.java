package com.cg.rabbitmq.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cg.rabbitmq.dao.UserDao;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDao dao ;

	@Override
	public int validateId(String userId) {
		return dao.validateId(userId);
	}

	@Override
	public int validatePassword(String password) {
		return dao.validatePassword(password);
	}

	@Override
	public String sendMessage() throws Exception {
		return dao.sendMessage();
	}

	@Override
	public void receiveMessage() throws Exception {
	}

}
