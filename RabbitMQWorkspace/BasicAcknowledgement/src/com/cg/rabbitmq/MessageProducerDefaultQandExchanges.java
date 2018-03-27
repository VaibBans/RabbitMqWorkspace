package com.cg.rabbitmq;


import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageProducerDefaultQandExchanges {

	private final static String QUEUE_NAME = "helloex";
	private static final String EXCHANGE = "ex";

	public static void main(String[] argv) throws Exception {
		Scanner sc = new Scanner(System.in);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare("", "direct",false);
		channel.queueDeclare("", false, false, false, null);
		channel.queueDeclare("", false, false, false, null);
		channel.queueBind("", "", "");

		System.out.println("Enter the message");
		String message = sc.nextLine();
		channel.basicPublish("", "", null, message.getBytes("UTF-8"));
		System.out.println(" [x] Sent '" + message + "'");
//		System.out.println("Enter the message");
//		String message1 = sc.nextLine();
//		channel.basicPublish("", "1", null, message1.getBytes("UTF-8"));
//		System.out.println(" [x] Sent '" + message1 + "'");

//		System.out.println("Number of messages "+channel.messageCount(QUEUE_NAME));

		channel.close();
		connection.close();
		sc.close();
	}
}