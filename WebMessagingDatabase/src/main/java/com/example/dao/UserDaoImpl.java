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
		return temp;
	}

	@Override
	public void sendMessage(String message) throws Exception {
		final String QUEUE_NAME = "basic_logs";
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);   
		System.out.println("Enter the number");	  
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		System.out.println(" [x] Sent '" + message + "'");
		connection.close();
	}

	@Override
	public String receiveMessage(String message) throws Exception {
		final String QUEUE_NAME = "basic_logs";
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
		return message;
	}
}