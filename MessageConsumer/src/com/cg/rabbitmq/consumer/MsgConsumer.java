package com.cg.rabbitmq.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MsgConsumer {
	private final static String QUEUE_NAME = "helloex11";
	private static final String EXCHANGE = "ex11";
	public static void recvMsg() throws Exception {
		Logger logger = LoggerFactory.getLogger(MsgConsumer.class);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
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
				logger.info("Received Message is "+message );
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume("", true, consumer);
	}

}
