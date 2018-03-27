package com.cg.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageConsumer {

	private static final String myExchange = "exchange1";
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String queue = channel.queueDeclare().getQueue();
		channel.exchangeDeclare(myExchange, "direct");
		channel.queueBind(queue, myExchange,"");
		
		System.out.println("Waiting for the messages");
		Consumer consumer = new DefaultConsumer(channel) {
		      @Override
		      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
		          throws IOException {
		        String message = new String(body, "UTF-8");
		        System.out.println(" [x] Received '" + message + "'");
		      }
		    };
		    channel.basicConsume("", true, consumer);

	}

}