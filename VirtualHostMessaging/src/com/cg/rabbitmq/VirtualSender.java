package com.cg.rabbitmq;


import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class VirtualSender {

	private final static String QUEUE_NAME = "vex";
	private static final String EXCHANGE = "vque";

	public static void main(String[] argv) throws Exception {
		Scanner sc = new Scanner(System.in);
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		factory.setUsername("user");
		factory.setPassword("book");
		factory.setVirtualHost("book_orders");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE, "direct",false);
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE, "");

		String message = "Hello World!";
		channel.basicPublish(EXCHANGE, "", null, message.getBytes("UTF-8"));
		System.out.println(" [x] Sent '" + message + "'");
		
		channel.close();
		connection.close();
		sc.close();
	}
}