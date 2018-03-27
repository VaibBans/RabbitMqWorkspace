package com.cg.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageSender {

	private static final String myAckQueue = "ackQueue";
	public static void main(String[] args) throws Exception{

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(myAckQueue, true, false, true, null);
		String message = "Message Acknowledgement";
		channel.basicPublish("", myAckQueue, null,message.getBytes());
		System.out.println("Message Sent: "+message);
	}
}