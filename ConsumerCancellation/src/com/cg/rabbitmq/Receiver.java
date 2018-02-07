package com.cg.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class Receiver {
	private final static String CONSUMER_QUEUE = "consumer_logs2";

	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();			
//		Map<String, Object> arguments = new HashMap<String,Object>();
//		arguments.put("x-queue-mode", "lazy");
		channel.queueDeclare(CONSUMER_QUEUE,false,true,false,null);
		System.out.println("Waiting for the message");
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleCancel(String consumerTag)throws IOException {
//				String message = new String(body, "UTF-8");
//				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(CONSUMER_QUEUE,consumer);
	}

}
