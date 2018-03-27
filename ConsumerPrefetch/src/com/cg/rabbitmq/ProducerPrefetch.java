package com.cg.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerPrefetch {
	private final static String PREFETCH_QUEUE = "prefetch_logs";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(PREFETCH_QUEUE, false, false, false, null);
		String message = "Hi";
		System.out.println("Message Sent: " + message);
		channel.basicPublish("", PREFETCH_QUEUE, null, null);
	}
}