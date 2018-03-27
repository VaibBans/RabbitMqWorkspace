package com.example.dao;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Component
public class UserDaoImpl implements UserDao {

	@Override
	public boolean validateUsername(String userName) {
		boolean temp = false;
		if(userName.equals("rabbit"))
			temp = true;
		return temp;
	}

	@Override
	public boolean validatePassword(String password) {
		boolean temp = false;
		if(password.equals("rabbit"))
			temp = true;
		System.out.println("Validated");
		return temp;
	}
	
	private final static String QUEUE_NAME = "basic_logs";
	private final static String EXCHANGE_NAME = "basic_ex";

	@Override
	public void sendMessage(String message) throws Exception {
		System.out.println("Welcome to sender");
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", false);
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		
		System.out.println("Sending the message");	  
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		System.out.println(" [x] Sent '" + message + "'");
	}

	@Override
	public String receiveMessage() throws Exception {
		String message = "";
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", false);
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, false, consumer);
		return message;
	}

	/*@Override
	public int checkContent(String message) throws Exception {
		int count = 0;
		message = receiveMessage(message);
		char ch[] = new char[message.length()+1];
		for(int i = 0;i<message.length();i++){
			ch[i] =	message.charAt(i);
			if((ch[i]>=65&&ch[i]<90)||(ch[i]>=97&&ch[i]<122)){
				count=0;
			}
			else{
				count = 1;
			}
		}
		if(count==0)	{
			System.out.println(" [x] Received '" + message + "'");
			System.out.println("Message receiving through: "+envelope.getExchange());
		}	        
		else if(count==1){
			channel.basicReject(envelope.getDeliveryTag(), false);
			System.out.println(envelope.getDeliveryTag());
			System.out.println("Message contains other than Characters");
			System.out.println("Message receiving through: "+envelope.getExchange());
		}		return 0;
	}*/
}