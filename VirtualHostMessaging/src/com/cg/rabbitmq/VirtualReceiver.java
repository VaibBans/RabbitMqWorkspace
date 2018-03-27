package com.cg.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class VirtualReceiver {

	private final static String QUEUE_NAME = "vex";
	private static final String EXCHANGE = "vque";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		factory.setUsername("user");
		factory.setPassword("book");
		factory.setVirtualHost("book_orders");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE, "direct",false);
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE, "");
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		System.out.println("In response");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume("", true, consumer);
	}
}
