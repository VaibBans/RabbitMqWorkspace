package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender2 {

	private static final String QUEUE2 = "priority2";
	private static final String EXCHANGE2 = "priex1";	
		
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE2, "fanout",false);
//	    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().priority(9).expiration("9000").build();

		Map<String, Object> arg2 = new HashMap<String, Object>();
//		arg2.put("x-message-ttl", 7000);
		arg2.put("x-max-priority",9);
		channel.queueDeclare(QUEUE2, false, false, false, arg2);
		channel.exchangeDeclare(EXCHANGE2, "fanout",false,false,false,arg2);
		channel.queueBind(QUEUE2, EXCHANGE2, "");
				
		String message = "message2";
		channel.basicPublish(EXCHANGE2, "", null,message.getBytes());
		System.out.println("Message Sent Successfully");
	}

}
