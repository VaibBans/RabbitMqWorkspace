package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import com.rabbitmq.client.QueueingConsumer;


public class ReceiverLowPriority {
	private final static String PRIORITY_CONSUMER = "priorities_logs";
	private final static String EXCHANGE_NAME = "exchange_logs";

	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(PRIORITY_CONSUMER, false, false, false, null);
		channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
		channel.queueBind(PRIORITY_CONSUMER,EXCHANGE_NAME,"");
		System.out.println("Waiting for the message");
		QueueingConsumer consumer = new QueueingConsumer(channel);
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("x-priority", 5);
		channel.basicConsume(PRIORITY_CONSUMER, false, "", false, false, arguments, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println("Received '" + message + "'");
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}

}


