package com.cg.rabbitmq;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessagePublisher {

	private final static String EXCHANGE = "exchange_value2";
	private final static String QUEUE2 = "queue_value2";

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE,"direct",false);
		channel.queueDeclare(QUEUE2, false, false, false, null);
		channel.queueBind(QUEUE2, EXCHANGE, "");

		System.out.println("Enter the message");
		String message = sc.nextLine(); 
		channel.basicPublish(EXCHANGE, "", null, message.getBytes());
		System.out.println(message+ " sent successfully");
		sc.close();
	}
}