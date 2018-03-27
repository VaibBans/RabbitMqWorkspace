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

	private final static String SIMPLE_QUEUE = "simple_queue3";
	private final static String SIMPLE_EXCHANGE = "simple_exchange3";
	private final static String DEAD_LETTER_QUEUE = "dead_letter_queue3";
	private final static String DEAD_LETTER_EXCHANGE = "dead_letter_exchange3";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(SIMPLE_EXCHANGE, BuiltinExchangeType.FANOUT);
		channel.exchangeDeclare(DEAD_LETTER_EXCHANGE, BuiltinExchangeType.FANOUT);
		
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
//		arguments.put("x-max-length",2);
		arguments.put("x-message-ttl", 2000);

		channel.queueDeclare(SIMPLE_QUEUE, false, false, false, arguments);
		channel.queueBind(SIMPLE_QUEUE, SIMPLE_EXCHANGE,"");

		channel.queueDeclare(DEAD_LETTER_QUEUE, false, false, false, null);
		channel.queueBind(DEAD_LETTER_QUEUE, DEAD_LETTER_EXCHANGE,"");

		System.out.println("Waiting for the message");
//		Thread.sleep(4000);
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
				System.out.println("Message receiving through: "+envelope.getExchange());
				System.out.println("Message receiving through: "+envelope.getRoutingKey());
//				channel.basicReject(envelope.getDeliveryTag(), false);
			}			
		};
		channel.basicConsume(DEAD_LETTER_QUEUE, false, consumer); // for messages in dead letter queue
		channel.basicConsume(SIMPLE_QUEUE, false,consumer);   // for messages in live queue
	}
}