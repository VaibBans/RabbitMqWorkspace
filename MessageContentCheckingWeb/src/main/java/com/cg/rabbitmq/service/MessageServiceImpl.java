package com.cg.rabbitmq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.rabbitmq.dao.MessageDao;

@Component
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageDao dao;
	@Override
	public boolean validateUserName(String userName) {
		// TODO Auto-generated method stub
		return dao.validateUserName(userName);
	}

	@Override
	public boolean validatePassword(String password) {
		// TODO Auto-generated method stub
		return dao.validatePassword(password);
	}

	@Override
	public boolean sendMessage(String message) throws Exception {
		// TODO Auto-generated method stub
		return dao.sendMessage(message);
	}

	@Override
	public boolean receiveMessage() throws Exception {
		// TODO Auto-generated method stub
		return dao.receiveMessage();
	}

	@Override
	public int checkContent(String message) throws Exception {
		return dao.checkContent(message);
	}

	@Override
	public int numberOfMessages() throws Exception {
		// TODO Auto-generated method stub
		return dao.numberOfMessages();
	}

	@Override
	public boolean checkContentInFile() throws Exception {
		// TODO Auto-generated method stub
		return dao.checkContentInFile();
	}

	@Override
	public void clearFileContent() throws Exception {
		// TODO Auto-generated method stub
		dao.clearFileContent();
	}
}