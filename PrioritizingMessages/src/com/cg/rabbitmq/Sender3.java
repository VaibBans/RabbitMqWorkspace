package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender3 {

	private static final String QUEUE3 = "priority3";
	private static final String EXCHANGE3 = "priex1";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE3, "fanout",false);
//	    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().priority(7).expiration("7000").build();

		Map<String, Object> arg3 = new HashMap<String, Object>();
//		arg3.put("x-message-ttl", 10000);
		arg3.put("x-max-priority",7);
		channel.queueDeclare(QUEUE3, false, false, false, arg3);
		channel.exchangeDeclare(EXCHANGE3, "fanout",false,false,false,arg3);
		channel.queueBind(QUEUE3, EXCHANGE3, "");
		
		String message = "message3";
		channel.basicPublish(EXCHANGE3, "", null,message.getBytes());
		System.out.println("Message Sent Successfully");
	}
}