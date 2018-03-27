package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender1 {

	private static final String QUEUE1 = "priority1";
	private static final String EXCHANGE1 = "priex1";
	
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		Map<String, Object> arg1 = new HashMap<String, Object>();
		arg1.put("x-max-priority", 255);
		channel.queueDeclare(QUEUE1, false, false, false, arg1);
		channel.exchangeDeclare(EXCHANGE1, "direct",false,false,false,arg1);
		channel.queueBind(QUEUE1, EXCHANGE1, "");
	    
		int pri,i,n;
		System.out.println("How many Message you want to send");
		n = sc.nextInt();
		for(i=0;i<n;i++){
			System.out.println("Enter the priority");
			pri = sc.nextInt();
		    AMQP.BasicProperties properties1 = new AMQP.BasicProperties.Builder().priority(pri).build();
		    System.out.println("Enter the message");
			String message1 = sc.next();
			channel.basicPublish("", QUEUE1, properties1,message1.getBytes());
			System.out.println("Message Sent Successfully");
		}
		sc.close();
	}
}
