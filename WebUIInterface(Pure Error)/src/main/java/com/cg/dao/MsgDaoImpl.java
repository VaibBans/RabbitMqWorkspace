package com.cg.dao;

import org.springframework.stereotype.Component;

import com.cg.rabbitmq.consumer.MsgConsumer;
import com.cg.rabbitmq.producer.Producer;

@Component
public class MsgDaoImpl implements MsgDao {

	@Override
	public void sendMessage(String message) throws Exception {
		Producer.sendMsg(message);		
	}

	@Override
	public void receiveMessage(String message) throws Exception {
		MsgConsumer.recvMsg();
	}

}
