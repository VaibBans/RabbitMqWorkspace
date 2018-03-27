package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

	private final static String SIMPLE_QUEUE = "simple_queue3";
	private final static String SIMPLE_EXCHANGE = "simple_exchange3";
	private final static String DEAD_LETTER_QUEUE = "dead_letter_queue3";
	private final static String DEAD_LETTER_EXCHANGE = "dead_letter_exchange3";

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(SIMPLE_EXCHANGE, BuiltinExchangeType.FANOUT);
		channel.exchangeDeclare(DEAD_LETTER_EXCHANGE, BuiltinExchangeType.FANOUT);
		
		Map<String, Object> arguments = new HashMap<String, Object>();
//		arguments.put("x-max-length",2);
		arguments.put("x-message-ttl", 2000);
		arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
		
		channel.queueDeclare(SIMPLE_QUEUE, false, false, false, arguments);
		channel.queueBind(SIMPLE_QUEUE, SIMPLE_EXCHANGE,"");
		
		channel.queueDeclare(DEAD_LETTER_QUEUE, false, false, false, null);
		channel.queueBind(DEAD_LETTER_QUEUE, DEAD_LETTER_EXCHANGE, "");
		
		System.out.println("Enter the message");
		String message = sc.nextLine();
		channel.basicPublish(SIMPLE_EXCHANGE, SIMPLE_QUEUE, null, message.getBytes("UTF-8"));
		System.out.println("Message sent successfully");
		sc.close();
	}
}