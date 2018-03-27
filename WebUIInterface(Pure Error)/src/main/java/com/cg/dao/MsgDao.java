package com.cg.dao;

public interface MsgDao {
	public void sendMessage(String message) throws Exception;
	public void receiveMessage(String message) throws Exception;
}
