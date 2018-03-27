package com.cg.rabbitmq;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageSender {

	private final static String MAX_LENGTH = "length_logs3";
	public static void main(String[] args) throws Exception{

		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		Map<String, Object> argus = new HashMap<String,Object>();	
		argus.put("x-max-length", 1);
		channel.queueDeclare(MAX_LENGTH,false,false,false,argus);
		System.out.println("Please enter your message");
		String message = sc.nextLine();
		System.out.println("Message Sent :"+message);
		channel.basicPublish("",MAX_LENGTH, null, message.getBytes("UTF-8"));

		sc.close();
	}

}
