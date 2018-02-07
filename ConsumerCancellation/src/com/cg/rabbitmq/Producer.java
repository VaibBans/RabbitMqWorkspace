package com.cg.rabbitmq;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	private final static String CONSUMER_QUEUE = "consumer_logs2";

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();			
//		Map<String, Object> arguments = new HashMap<String,Object>();
//		arguments.put("x-queue-mode", "lazy");
		channel.queueDeclare(CONSUMER_QUEUE,false,true,false,null);
		System.out.println("Enter the message:");
		//		String message = sc.nextLine();
//		long n = sc.nextLong();
//		for(int i = 0;i<n;i++){
			String message = sc.nextLine();
			System.out.println("Message Sent: "+ message);
			channel.basicPublish("", CONSUMER_QUEUE, null, message.getBytes("UTF-8"));
			sc.close();
//		}
	}
}
