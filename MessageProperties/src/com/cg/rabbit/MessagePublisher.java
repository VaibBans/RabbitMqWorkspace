package com.cg.rabbit;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessagePublisher {

	private static final String myPropQueue = "propQueue";
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(myPropQueue, true, false, true, null);
		Map<String,Object>headerMap = new HashMap<String,
				Object>();
				headerMap.put("key1", "value1");
				headerMap.put("key2", new Integer(50) );
				headerMap.put("key3", new Boolean(false));
				headerMap.put("key4", "value4");
				
				BasicProperties messageProperties = new
						BasicProperties.Builder()
						.timestamp(new Date())
						.contentType("text/plain")
						.userId("guest")
						.appId("app id: 20")
						.deliveryMode(1)
						.priority(1)
						.headers(headerMap)
						.clusterId("cluster id: 1")
						.build();
		String message = "Message Acknowledgement";
		channel.basicPublish("", myPropQueue, messageProperties,message.getBytes());
		System.out.println("Message Sent: "+message);
	}

}
