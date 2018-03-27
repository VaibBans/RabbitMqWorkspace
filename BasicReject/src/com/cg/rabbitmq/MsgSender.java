package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MsgSender {
	private static final String QUEUE1 = "basicReject";
	private static final String EXCHANGE1 = "basicRejectEx";
	private final static String DEAD_LETTER_QUEUE = "basicReject_dead_letter_queue";
	private final static String DEAD_LETTER_EXCHANGE = "basicReject_dead_letter_exchange";

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE1, "fanout",false);
		channel.exchangeDeclare(DEAD_LETTER_EXCHANGE,  "fanout",false);

		Map<String , Object> map = new HashMap<>();
		map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
		
		channel.queueDeclare(QUEUE1, false, false, false, map);
		channel.queueBind(QUEUE1, EXCHANGE1, "");
		
//		channel.queueDeclare(DEAD_LETTER_QUEUE, false, false, false, null);
		channel.queueBind(QUEUE1, DEAD_LETTER_EXCHANGE, "");
		
		System.out.println("Enter the message");
		String message = sc.nextLine();
		channel.basicPublish(EXCHANGE1,QUEUE1, null,message.getBytes());
//		channel.basicPublish(DEAD_LETTER_EXCHANGE,DEAD_LETTER_QUEUE, null,message.getBytes());
		System.out.println("Message Sent Successfully");
		sc.close();
	}

}
