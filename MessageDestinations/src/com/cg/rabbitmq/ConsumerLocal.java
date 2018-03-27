package com.cg.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConsumerLocal {
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
		String myQueue = channel.queueDeclare().getQueue();
		channel.queueBind(myQueue,EXCHANGE, ALERT_ROUTING_KEY);

		String myQueueCC_BK = channel.queueDeclare().getQueue();
		channel.queueBind(myQueueCC_BK, EXCHANGE, BACKUP_ALERT_ROUTING_KEY);

		String myQueueBCC_SA = channel.queueDeclare().getQueue();
		channel.queueBind(myQueueBCC_SA,EXCHANGE,SEND_ALERT_ROUTING_KEY);

		CCStatsConsumer ccConsumer = new CCStatsConsumer(channel);
		channel.basicConsume(myQueueCC_BK, true,ccConsumer).getBytes().toString();

		BCCStatsConsumer bccConsumer = new BCCStatsConsumer(channel);
		channel.basicConsume(myQueueBCC_SA, true, bccConsumer);

		System.out.println("Waiting for the message");
		StatsConsumer statsConsumer = new StatsConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
				System.out.println("Routing Key: "+envelope.getRoutingKey());
				System.out.println("Exchange:- "+envelope.getExchange());
			}
		};
		channel.basicConsume(myQueue,true,statsConsumer);

		System.out.println("**************************************************");
		BCCStatsConsumer bccStatsConsumer = new BCCStatsConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
				System.out.println("Routing Key: "+envelope.getRoutingKey());
				System.out.println("Exchange:- "+envelope.getExchange());
			}
		};
		channel.basicConsume(myQueueBCC_SA,true,bccStatsConsumer);

		System.out.println("**************************************************");
		CCStatsConsumer ccStatsConsumer = new CCStatsConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
				System.out.println("Routing Key: "+envelope.getRoutingKey());
				System.out.println("Exchange:- "+envelope.getExchange());
			}
		};
		channel.basicConsume(myQueueCC_BK,true,ccStatsConsumer);
	}
}