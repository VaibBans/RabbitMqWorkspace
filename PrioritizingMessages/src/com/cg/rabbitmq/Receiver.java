package com.cg.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Receiver {

	private static final String QUEUE1 = "priority1";
	private static final String EXCHANGE1 = "priex1";

	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		Map<String, Object> arg1 = new HashMap<String, Object>();
		arg1.put("x-max-priority", 255);
		
		channel.exchangeDeclare(EXCHANGE1, "direct",false,false,false,arg1);
		channel.queueDeclare(QUEUE1, false, false, false, arg1);
		channel.queueBind(QUEUE1, EXCHANGE1, "");

		System.out.println("Waiting for the message");
		Consumer consumer1 = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE1, true, consumer1);
	}
}
