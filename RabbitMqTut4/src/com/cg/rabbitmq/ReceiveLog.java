package com.cg.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceiveLog {

	public static final String EXCHANGE_NAME = "direct_logs";
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.DIRECT);
		String queueName = channel.queueDeclare().getQueue();
		/*if(args.length<1) {
		System.err.println("Usage: [info][warning][error]");
		System.exit(1);
		}*/
		/*int i = 0;
		for(String severity: args) {
			i++;
			channel.queueBind(queueName,EXCHANGE_NAME,severity);
			System.out.println("Value of severity: "+severity);
			System.out.println("repetition: "+i);
		}*/
		System.out.println("Waiting for the messages ");
		Consumer consumer = new DefaultConsumer(channel) {		
		@Override
		public void handleDelivery(String ConsumerTag, Envelope envelope, AMQP.BasicProperties properties, 
					byte[] body) throws IOException{
				String message = new String(body, "UTF-8");
				System.out.println("message: "+message);
				System.out.println("Message Received: "/*+ envelope.getRoutingKey()*/+ "::--"+message);

			}
		};
		channel.basicConsume(queueName,true,consumer);
	}
}
