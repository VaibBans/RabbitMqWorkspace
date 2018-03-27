package com.cg.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MsgReceiver {
	private static final String QUEUE1 = "basicReject";
	private static final String EXCHANGE1 = "basicRejectEx";
	private final static String DEAD_LETTER_QUEUE = "basicReject_dead_letter_queue";
	private final static String DEAD_LETTER_EXCHANGE = "basicReject_dead_letter_exchange";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE1, "fanout",false);
		channel.exchangeDeclare(DEAD_LETTER_EXCHANGE, "fanout",false);

		Map<String , Object> map = new HashMap<>();
		map.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE);

		channel.queueDeclare(QUEUE1, false, false, false, map);
		channel.queueBind(QUEUE1, EXCHANGE1, "");

//		channel.queueDeclare(DEAD_LETTER_QUEUE, false, false, false, null);
		channel.queueBind(QUEUE1, DEAD_LETTER_EXCHANGE, "");

		System.out.println("Waiting for the message");
		Consumer consumer1 = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
				System.out.println("Message receiving through "+envelope.getExchange());
				channel.basicReject(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(QUEUE1, false, consumer1);
//		channel.basicConsume(DEAD_LETTER_QUEUE, false,consumer1);
	}
}