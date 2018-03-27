package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	private final static String LAZY_QUEUE = "lazy_logs";

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.3");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();			
		Map<String, Object> arguments = new HashMap<String,Object>();
		arguments.put("x-queue-mode", "lazy");
		channel.queueDeclare(LAZY_QUEUE,false,false,false,arguments);
		System.out.println("Enter the number:");
		//		String message = sc.nextLine();
		long n = sc.nextLong();
		for(int i = 0;i<n;i++){
			String message = i+1+". Hello";
			System.out.println(i+1+"." +"Message Sent: "+ message);
			channel.basicPublish("", LAZY_QUEUE, null, message.getBytes("UTF-8"));
			sc.close();
		}
	}
}