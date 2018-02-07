package com.cg.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Receiver {

	private final static String LETTER_QUEUE = "letter_logs4";
	private final static String LETTER_EXCHANGE = "exchange_letter_logs4";
	private final static String ANOTHER_QUEUE = "queue_logs4";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(LETTER_EXCHANGE, BuiltinExchangeType.FANOUT);
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("x-dead-letter-exchange", LETTER_EXCHANGE);
//		arguments.put("x-max-length",2);
		channel.queueDeclare(LETTER_QUEUE, false, false, false, arguments);
		channel.queueDeclare(ANOTHER_QUEUE, false, false, false, arguments);
		channel.queueBind(LETTER_QUEUE, LETTER_EXCHANGE,"*",null);
		System.out.println("Waiting for the message");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(LETTER_QUEUE, true, consumer);
	}
}
