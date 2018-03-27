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

public class MessageReceiver {

	private static final String queue = "spcqueue1";
	private static final String exchange = "spcexc1";

	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		Map<String,Object> arg = new HashMap<>();
		arg.put("x-message-ttl", 10000);
		channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT);
		channel.queueDeclare(queue,false,false,false,arg);
		channel.queueBind(queue, exchange, "");
		
		System.out.println("Waiting for the message");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};	
		channel.basicConsume(queue, true,consumer);
	}
}