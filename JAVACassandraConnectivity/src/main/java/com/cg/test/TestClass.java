/*package com.cg.test;
package com.cg.guava.cache.cassandra;

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

public class TestClass {

	private final static String QUEUE_NAME = "cachelogs";

	public static void main(String[] argv) throws Exception {

		LoadingCache<String, String> employeeCache = CacheBuilder.newBuilder().maximumSize(10)
				.expireAfterWrite(10, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
					@Override
					public String load(String message) throws Exception {
						return loadMessages(message);
					}
				});

		System.out.println("In main value of postMessage is:- "+postMessage.get(1L));
		
		String msg = MessageConsumer.loadMessages("B");
		System.out.println("Received Message before cache:- " + msg);

		employeeCache.asMap().put("1", msg);
		System.out.println("Received Message in Cache:- " + employeeCache.asMap().get("1"));	
	
	TestClass.loadMessages("A");	
	
	
	
	}

	static Map<String, String> messageDB = new HashMap<>();
	static String message = null;

	public static String loadMessages(String key) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
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
								return loadMessages(message);
							}
						});
				employeeCache.asMap().put("1", message);
//				System.out.println("In main value of postMessage is:- "+postMessage.get(1L));
				
//				System.out.println("Received Message in Cache:- " + employeeCache.asMap().get("1"));

				System.out.println("----------------------------------------------------------------");
				System.out.println("Direct value of message in cache: "+employeeCache.asMap().get("1"));
				System.out.println("Receiving message in handleDelivery:- " + messageDB.get("A"));
				MessageConsumer.sendMessage(message);
				System.out.println("Printing value of message through function:- "+sendMessage(message));
			}
		};

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		String alterMessage = postMessage.get(1);
		System.out.println("Message value is: " + alterMessage);
		messageDB.put("B", message);
//		System.out.println("Receiving message outside the function handleDelivery:- " + employ.get("B"));

		channel.basicConsume(QUEUE_NAME, true, consumer);
		return messageDB.get(key);
	}

	static Map<Integer, String> postMessage = new HashMap<>();

	public static String sendMessage(String message) {
		System.out.println("In sendMessage function value of message is "+message);
		postMessage.put(1, message);
		System.out.println("Through postMessage map "+postMessage.get(1));
//		return postMessage.get(1);
		return message;
	}
	public static  String sendingMessage(){
		String msg = MessageConsumer.sendMessage(message);
	}
}*/