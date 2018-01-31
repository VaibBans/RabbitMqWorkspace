package com.cg.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceiverWorkQueues {
	private final static String TASK_QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();
	channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
	System.out.println("Waiting for the message");
	channel.basicQos(1);
	final Consumer consumer = new DefaultConsumer(channel){
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope,
	AMQP.BasicProperties properties, byte[] body) throws IOException{
		String message = new String(body,"UTF-8");
		System.out.println("Mesage Received: " +message);
		try{
			doWork(message);
		}catch(InterruptedException ie){
			ie.getMessage();
		}
		finally{
			System.out.println("[x] Done");
			channel.basicAck(envelope.getDeliveryTag(),false);
		}
	}
	};
	boolean autoAck = true;
	channel.basicConsume(TASK_QUEUE_NAME,autoAck,consumer);
	}
	public static void doWork(String task) throws InterruptedException{
		for(char ch: task.toCharArray()){
			if(ch=='.')
			Thread.sleep(1000);
	}
	}
}