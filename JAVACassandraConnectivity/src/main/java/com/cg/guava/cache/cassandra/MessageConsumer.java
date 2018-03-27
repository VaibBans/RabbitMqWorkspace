package com.cg.guava.cache.cassandra;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.message_cache.cassandra.JAVACassandraConnectivity.CassandraMethods;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageConsumer {

	private final static String QUEUE_NAME = "cachelogs";
	static String message = null;
	static 	Map<String, String> messageDB = new HashMap<>();

	public static void main(String[] argv) throws Exception {
		loadMessages("A");
	}

	public static String loadMessages(String key) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				message = new String(body,"UTF-8");
				messageDB.put("A", message);
				System.out.println(" [x] Received '" + message + "'");
				LoadingCache<String, String> employeeCache = CacheBuilder.newBuilder().maximumSize(10)
						.expireAfterWrite(10, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
							@Override
							public String load(String message) throws Exception {
								return null/*loadMessages(message)*/;
							}
						});
		
				employeeCache.asMap().put("1", message);	
					System.out.println("Sleeping for 5 seconds");
				System.out.println("Received Message in Cache:- " + employeeCache.asMap().get("1"));
				try {
					System.out.println("In try block");
					CassandraMethods cassandraMethods = new CassandraMethods();
					cassandraMethods.insertIntoMessageDB(message,employeeCache.get("1"));
					System.out.println("In MessageConsumer Received Message in Cache:- " + employeeCache.asMap().get("1"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
		return messageDB.get(key);
	}

	public static String sendMessage(String message) {
		Map<Integer, String> postMessage = new HashMap<>();
		postMessage.put(1, message);
		sendingMessage(message);
		return message;
	}
	public static String sendingMessage(String Message){
		Map<Integer, String> afterMap = new HashMap<>();
		afterMap.put(1, message);
		return afterMap.get(1);
	}
}