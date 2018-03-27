package com.cg.rabbitmq;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MsgPublisher {
	private static final String QUEUE1 = "eventQ";
	private static final String EXCHANGE1 = "eventEx";
		
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE1, "fanout",false);
		channel.queueDeclare(QUEUE1, false, false, true, null);
		channel.queueBind(QUEUE1, EXCHANGE1, "");
		
		System.out.println("Enter the operation");
		String operation = sc.next();
		if("addition".equalsIgnoreCase(operation)||"multiply".equalsIgnoreCase(operation)
		 ||"subtraction".equalsIgnoreCase(operation)||"division".equalsIgnoreCase(operation)){
		System.out.println("Enter two numbers");
		System.out.println("Enter first number");
		int num1 = sc.nextInt();
		System.out.println("Enter second number");
		int num2 = sc.nextInt();
		String message = operation+" "+num1+ " "+num2;
		channel.basicPublish(EXCHANGE1, QUEUE1, true, null, message.getBytes());
		System.out.println("Message Sent Successfully");
		sc.close();
		}
		else{
			System.out.println("Operation Not listed \nVisit Again");
			System.exit(0);
		}
	}

}
