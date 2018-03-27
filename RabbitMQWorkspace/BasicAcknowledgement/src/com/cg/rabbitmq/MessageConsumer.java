package com.cg.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class MessageConsumer {

	private final static String QUEUE_NAME = "helloex";
	private static final String EXCHANGE = "ex";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare("", "direct",false);
		channel.queueDeclare("", false, false, false, null);
//		channel.queueDeclare("", false, false, false, null);
		channel.queueBind("", "", "");
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
		channel.basicConsume("",false,consumer);
		channel.basicConsume("",false,consumer);

	}
}