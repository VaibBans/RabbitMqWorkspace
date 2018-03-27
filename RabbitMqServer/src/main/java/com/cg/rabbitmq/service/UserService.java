package com.cg.rabbitmq.service;

public interface UserService {
	public int validateId(String userId);
	public int validatePassword(String password);
	public String sendMessage() throws Exception;
	public void receiveMessage() throws Exception;
}
