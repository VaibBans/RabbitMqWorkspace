package com.cg.rabbitmq;


import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageProducerAuth {

	private final static String QUEUE_NAME = "helloex";
	private static final String EXCHANGE = "ex";

	public static void main(String[] argv) throws Exception {
		Scanner sc = new Scanner(System.in);
		String userName ;
		String password;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		
		System.out.println("Enter User Name");
		userName = sc.nextLine();
		System.out.println("Enter Password");
		password = sc.nextLine();

		factory.setUsername(userName);
		factory.setPassword(password);

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