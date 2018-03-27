package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Receiver {
	private static final String DELAYED_QUEUE = "delayed_logs";
	private static final String DELAYED_EXCHANGE = "exChangeName";

	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		Map<String,Object> arguments = new HashMap<String,Object>();
		arguments.put("x-delay",10000);
		String message = null;
		channel.queueDeclare(DELAYED_QUEUE,false,false,false,arguments);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		Map<String,Object> arggs = new HashMap<String,Object>();
		arggs.put("x-delayed-type","direct");
		channel.exchangeDeclare(DELAYED_EXCHANGE, "x-delayed-message",true,false,arggs);
		channel.queueBind(DELAYED_QUEUE, DELAYED_EXCHANGE,"");
		channel.basicConsume(DELAYED_EXCHANGE, true,consumer);
		try{
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			message = (new String(delivery.getBody()));
		}catch(Exception exception){
			exception.printStackTrace();
		}
		System.out.println("Message Received: "+message);

	}

}
