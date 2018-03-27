package com.cg.rabbitmq;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class CCStatsConsumer extends DefaultConsumer {

	public CCStatsConsumer(Channel channel) {
		super(channel);
	}

	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
			throws java.io.IOException {
		String message = new String(body, "UTF-8");
		System.out.println(" [x] Received '" + message + "'");
		System.out.println("Delivery Tag: " + envelope.getDeliveryTag());
	}
}