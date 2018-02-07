package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

	private final static String LETTER_QUEUE = "letter_logs4";
	private final static String LETTER_EXCHANGE = "exchange_letter_logs4";
	private final static String ANOTHER_QUEUE = "queue_logs4";

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(LETTER_EXCHANGE, BuiltinExchangeType.FANOUT);
		Map<String, Object> arguments = new HashMap<String, Object>();
//		arguments.put("x-max-length",2);
		arguments.put("x-dead-letter-exchange", LETTER_EXCHANGE);
		channel.queueDeclare(LETTER_QUEUE, false, false, false, arguments);
		channel.queueDeclare(ANOTHER_QUEUE, false, false, false, arguments);
		channel.queueBind(LETTER_QUEUE, LETTER_EXCHANGE,"*",null);
		System.out.println("Enter the message");
		String message = sc.nextLine();
		channel.basicPublish(LETTER_EXCHANGE, ANOTHER_QUEUE, null, message.getBytes("UTF-8"));
		sc.close();
	}
}