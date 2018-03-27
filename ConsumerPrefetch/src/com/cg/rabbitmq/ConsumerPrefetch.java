package com.cg.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConsumerPrefetch {
	private final static String PREFETCH_QUEUE = "prefetch_logs";

	public static void main(String[] args) throws Exception {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(PREFETCH_QUEUE, false, false, false, null);
			System.out.println("Waiting for the messages");
			channel.basicQos(1);
			Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body) throws IOException{
				String message = new String(body,"UTF-8");
				System.out.println("Mesage Received: " +message);		
			}
			};
			channel.basicConsume(PREFETCH_QUEUE	,true, consumer);
	}

}
