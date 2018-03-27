package com.cg.rabbit;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageConsumer {

	private static final String myPropQueue = "propQueue";

	public static void main(String[] args) throws Exception{

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(myPropQueue, true, false, true, null);
		System.out.println("Waiting for message");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("***********message header****************");
				System.out.println("Message sent at:"+ properties.getTimestamp());
				System.out.println("Message sent by user:"+ properties.getUserId());
				System.out.println("Message sent by App:"+properties.getAppId());
				System.out.println("all properties :" + properties.toString());
				System.out.println("**********message body**************");
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(myPropQueue, consumer);
	}

}
