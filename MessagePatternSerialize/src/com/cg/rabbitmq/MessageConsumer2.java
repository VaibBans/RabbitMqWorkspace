package com.cg.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageConsumer2 extends DefaultConsumer{

	public MessageConsumer2(Channel channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}

	private final static String EXCHANGE = "exchange_value2";
	private final static String QUEUE2 = "queue_value2";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE,"direct",false);
		channel.queueDeclare(QUEUE2, false, false, false, null);
		channel.queueBind(QUEUE2, EXCHANGE, "");

		/*String queue2 = channel.queueDeclare().getQueue();
		channel.queueBind(queue2, EXCHANGE, "");
*/
		/*GetResponse response = channel.basicGet(QUEUE2, true);
		String res1 = response.toString();
		System.out.println("Response "+res1);*/
		System.out.println("Waiting for the message");
			System.out.println("In response");
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
						throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(" [x] Received '" + message + "'");
				}
			};
			channel.basicConsume("", true, consumer);
		}
}
