package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

	private static final String DELAYED_QUEUE = "delayed_logs";
	private static final String DELAYED_EXCHANGE = "exchange_name";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		String message = "This message is delayed by 10 seconds";
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		Map<String, Object> arggs = new HashMap<String, Object>();
		arggs.put("x-delayed-type", "direct");
		channel.exchangeDeclare(DELAYED_EXCHANGE, "x-delayed-message", true, false, arggs);
		Map<String, Object> arguments = new HashMap<String, Object>();
		channel.queueDeclare(DELAYED_QUEUE, false, false, false, null);
		arguments.put("x-delay", 1000);
		AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder().headers(arguments);
		channel.basicPublish(DELAYED_EXCHANGE, DELAYED_QUEUE, properties.build(), message.getBytes("UTF-8"));
		System.out.println("Message sent: " + message);
	}

}
