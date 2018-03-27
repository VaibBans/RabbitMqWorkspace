package com.example.dao;

public interface UserDao {

	public boolean validateUsername(String userName);
	public boolean validatePassword(String password);
	public void sendMessage(String message) throws Exception;
	public String receiveMessage() throws Exception;
//	public int checkContent(String message) throws Exception;
}
