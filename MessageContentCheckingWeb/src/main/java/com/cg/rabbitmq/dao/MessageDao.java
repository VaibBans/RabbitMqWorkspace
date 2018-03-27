package com.cg.rabbitmq.dao;

public interface MessageDao {

	public boolean validateUserName(String userName);
	public boolean validatePassword(String password);
	public boolean sendMessage(String message) throws Exception;
	public boolean receiveMessage() throws Exception;
	public int checkContent(String message) throws Exception;
	public int numberOfMessages() throws Exception;
	public boolean checkContentInFile() throws Exception;
	public void clearFileContent() throws Exception;
}