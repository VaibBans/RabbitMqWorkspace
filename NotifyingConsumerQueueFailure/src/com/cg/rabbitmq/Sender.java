package com.cg.rabbitmq;

import java.util.Scanner;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

	private final static String SIMPLE_QUEUE = "queue_del_notif";
	private final static String SIMPLE_EXCHANGE = "exchange_notif";

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(SIMPLE_EXCHANGE, BuiltinExchangeType.FANOUT);

		channel.queueDeclare(SIMPLE_QUEUE, false, false, false, null);
		channel.queueBind(SIMPLE_QUEUE, SIMPLE_EXCHANGE,"");
		
		System.out.println("Enter the message");
		String message = sc.nextLine();
		channel.basicPublish(SIMPLE_EXCHANGE, SIMPLE_QUEUE, null, message.getBytes("UTF-8"));
		System.out.println("Message sent successfully");
//		channel.queueDelete(SIMPLE_QUEUE);	
		sc.close();
	}
}