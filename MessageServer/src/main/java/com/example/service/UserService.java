package com.example.service;

public interface UserService {
	public boolean validateUsername(String userName);
	public boolean validatePassword(String password);
	public void sendMessage(String message) throws Exception;
	public String receiveMessage() throws Exception;
	
/*	public String getMessageFromCache() throws Exception;
	public void putMessage(String messageToCache);*/
	
	
}
