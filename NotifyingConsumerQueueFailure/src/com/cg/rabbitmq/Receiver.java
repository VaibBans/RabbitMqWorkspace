package com.cg.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ReturnListener;

public class Receiver {

	private final static String SIMPLE_QUEUE = "queue_del_notif";
	private final static String SIMPLE_EXCHANGE = "exchange_notif";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(SIMPLE_EXCHANGE, BuiltinExchangeType.FANOUT);
		
		channel.queueDeclare(SIMPLE_QUEUE, false, false, false, null);
		channel.queueBind(SIMPLE_QUEUE, SIMPLE_EXCHANGE,"");

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
			public void handleCancel(String consumerTag){
				System.out.println("Queue Deleted");
			}
		};
		channel.basicConsume(SIMPLE_QUEUE, false,consumer);   // for messages in live queue
	}
}