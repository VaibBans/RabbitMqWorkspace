package com.cg.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

	private static final String loadQueue = "loadbalancer";
	public static void main(String[] args) throws Exception{

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(loadQueue,false, false, false, null);
		String message = "Load Balancer";
		channel.basicPublish("", loadQueue, null, message.getBytes());
		System.out.println("Message sent: "+message);
	}
}