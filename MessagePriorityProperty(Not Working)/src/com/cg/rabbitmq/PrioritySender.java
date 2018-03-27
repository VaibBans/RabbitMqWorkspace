package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PrioritySender {

	private static final String priQueue = "msgPriority";
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		int a;

		channel.queueDeclare(priQueue, false, false, false, null);

		System.out.println("How many messages you want to enter");
		a = sc.nextInt();
		for(int i=1;i<=a;i++){
			System.out.println("Enter the messages");
			String message = sc.next();
			BasicProperties properties = new BasicProperties().builder().priority(i).build();
			channel.basicPublish("", priQueue, properties, message.getBytes());
		}
		System.out.println(" [x] Sent " );
		channel.close();
		connection.close();
		sc.close();
	}

}
