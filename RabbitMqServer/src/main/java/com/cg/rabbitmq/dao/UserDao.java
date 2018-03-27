package com.cg.rabbitmq.dao;

public interface UserDao {
	public int validateId(String userId);
	public int validatePassword(String password);
	public String sendMessage() throws Exception;
	public void receiveMessage() throws Exception;
}
