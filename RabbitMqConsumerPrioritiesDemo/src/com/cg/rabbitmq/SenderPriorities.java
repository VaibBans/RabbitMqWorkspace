package com.cg.rabbitmq;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderPriorities {
	private final static String PRIORITY_CONSUMER = "priorities_logs";
	private final static String EXCHANGE_NAME = "exchange_logs";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(PRIORITY_CONSUMER, false, false, false, null);
		System.out.println("Enter your message");
		Scanner sc = new Scanner(System.in);
		String message = sc.nextLine();

		channel.basicPublish("", PRIORITY_CONSUMER,null, message.getBytes("UTF-8"));		
		System.out.println(" [x] Sent '" + message + "'");
		
		channel.close();
		connection.close();
		sc.close();
	}
}