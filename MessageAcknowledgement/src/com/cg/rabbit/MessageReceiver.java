package com.cg.rabbit;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageReceiver {

	private static final String myAckQueue = "ackQueue";
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(myAckQueue, true, false, true, null);
		System.out.println("Waiting for message");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				long deliveryTag = envelope.getDeliveryTag();
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
//				channel.basicAck(deliveryTag,false);
			}
		};
//		boolean autoAck = true;
		channel.basicConsume(myAckQueue, consumer);
	}
}