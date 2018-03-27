package com.cg.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Receiver {

	private static final String QUEUE1 = "check1";
	private static final String EXCHANGE1 = "checkex1";
	private final static String DEAD_LETTER_QUEUE = "check_dead_letter_queue1";
	private final static String DEAD_LETTER_EXCHANGE = "check_dead_letter_exchange1";

	public static void main(String[] args) throws Exception{
		Logger logger = LoggerFactory.getLogger(Sender.class);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.17.0.2");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE1, "fanout",false);
		channel.exchangeDeclare(DEAD_LETTER_EXCHANGE, BuiltinExchangeType.FANOUT);

		Map<String , Object> map = new HashMap<>();
		map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);

		channel.queueDeclare(QUEUE1, false, false, false, map);
		channel.queueBind(QUEUE1, EXCHANGE1, "");

		channel.queueDeclare(DEAD_LETTER_QUEUE, false, false, false, null);
		channel.queueBind(DEAD_LETTER_QUEUE, DEAD_LETTER_EXCHANGE, "");

		logger.info("Waiting for the message");
		Consumer consumer1 = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				int count = 0;
				String message = new String(body, "UTF-8");
				char ch[] = new char[message.length()+1];
				for(int i = 0;i<message.length();i++){
					ch[i] =	message.charAt(i);
					if((ch[i]>=65&&ch[i]<90)||(ch[i]>=97&&ch[i]<122)){
						count=0;
					}
					else{
						count = 1;
					}
				}
				if(count==0)	{
					logger.info(" [x] Received '" + message + "'");
					logger.info("Message receiving through: "+envelope.getExchange());
				}	        
				else if(count==1){
					channel.basicReject(envelope.getDeliveryTag(), false);
					logger.info("Delivery Tag is- "+envelope.getDeliveryTag());
					logger.info("Message contains other than Characters");
					logger.info("Message receiving through: "+envelope.getExchange());
				}
			}
		};
		channel.basicConsume(QUEUE1, false, consumer1);
		channel.basicConsume(DEAD_LETTER_QUEUE, false,consumer1);
	}
}