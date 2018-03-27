package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageSender {

	private static final String queue = "spcqueue1";
	private static final String exchange = "spcexc1";

	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		Map<String,Object> arg = new HashMap<>();
		arg.put("x-message-ttl", 10000);
		channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT);
		channel.queueDeclare(queue,false,false,false,arg);
		channel.queueBind(queue, exchange, "");

		String message = "time-to-live specific queues";
		channel.basicPublish(exchange, queue, null, message.getBytes("UTF-8"));
		System.out.println(message+" Sent Successfully");
	}
}