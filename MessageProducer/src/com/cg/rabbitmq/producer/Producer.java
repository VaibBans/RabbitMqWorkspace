package com.cg.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
	private final static String QUEUE_NAME = "helloex11";
	private static final String EXCHANGE = "ex11";
	
	public static void sendMsg(String message) throws Exception {
		Logger logger = LoggerFactory.getLogger(Producer.class);
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE, "direct",false);
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE, "");

		channel.basicPublish(EXCHANGE, "", null, message.getBytes("UTF-8"));
		logger.info(" [x] Sent '" + message + "'");
		System.out.println("Sending message");
		logger.info("Message Sent");
		System.out.println();
		channel.close();
		connection.close();
	}
}