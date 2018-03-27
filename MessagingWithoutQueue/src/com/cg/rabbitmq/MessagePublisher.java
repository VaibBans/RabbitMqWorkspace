package com.cg.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessagePublisher {

	private final static String myExchange = "exchange1";
	public static void main(String[] args) throws Exception{

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(myExchange, "direct");
		boolean isMandatory = true;
		
		String message = "Without Queue";
		channel.basicPublish(myExchange, "", isMandatory,null, message.getBytes());
		System.out.println("Message Sent");
	}

}
