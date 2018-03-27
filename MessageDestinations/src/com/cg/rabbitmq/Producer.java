package com.cg.rabbitmq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	private static final String EXCHANGE = "stats_exchange1";
	private static final String ALERT_ROUTING_KEY = "alert_rkey1";
	private static final String BACKUP_ALERT_ROUTING_KEY = "backup_alert_rkey1";
	private static final String SEND_ALERT_ROUTING_KEY = "send_alert_rkey1";

	public static void main(String[] args) throws Exception{

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE,"fanout",false);

		Map<String, Object> header = new HashMap<String, Object>();
		List<String> ccList = new ArrayList<String>();
		ccList.add(BACKUP_ALERT_ROUTING_KEY);
		header.put("CC", ccList);

		List<String> bccList = new ArrayList<String>();
		bccList.add(SEND_ALERT_ROUTING_KEY);
		header.put("BCC", bccList);

		String message = "Message Destinations";
		BasicProperties properties = new BasicProperties().builder().headers(header).build();
		channel.basicPublish(EXCHANGE, ALERT_ROUTING_KEY, properties, message.getBytes());
		System.out.println(message+" Sent Successfully");
	}

}
