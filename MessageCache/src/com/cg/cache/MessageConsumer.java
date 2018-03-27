package com.cg.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageConsumer {

	private final static String QUEUE_NAME = "cachelogs";

	public static void main(String[] argv) throws Exception {

		LoadingCache<String, String> employeeCache = CacheBuilder.newBuilder().maximumSize(10).
				expireAfterWrite(10, TimeUnit.SECONDS).build(new CacheLoader<String, String>(){
					@Override
					public String load(String message) throws Exception {
						return loadMessages(message);
					} 
				});  
		
		System.out.println("Message receiving from load Message function:- "+messageDB.get("A"));
		
		String msg = MessageConsumer.loadMessages("A");
		System.out.println("Received Message before cache:- "+msg);
		
		employeeCache.asMap().put("1", msg);
		System.out.println("Received Message in Cache:- "+employeeCache.get("1"));

	}
	static Map<String, String> messageDB = new HashMap<>(); 
	public static String loadMessages(String key) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				messageDB.put("A", message);
				System.out.println(" [x] Received '" + message + "'");
				System.out.println("Receiving message in handleDelivery:- "+messageDB.get("A"));
			}
		};
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		System.out.println("Receiving message outside the function handleDelivery:- "+messageDB.get("A"));
		
		channel.basicConsume(QUEUE_NAME, true, consumer);
		return messageDB.get(key);
	}
}