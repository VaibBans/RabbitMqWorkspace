package com.cg.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Publisher implements Runnable {
	
	
	public Publisher() {
		super();
	}
	static int msgCount = 10000;
	final static String QUEUE_NAME = "confirm-test";
	static ConnectionFactory connectionFactory;
	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			Connection conn = connectionFactory.newConnection();
			Channel ch = conn.createChannel();
			ch.queueDeclare(QUEUE_NAME, true, false, false, null);
			ch.confirmSelect();

			for (long i = 0; i < msgCount; ++i) {
				ch.basicPublish("", QUEUE_NAME,	MessageProperties.PERSISTENT_BASIC,	"nop".getBytes());
			}

			ch.waitForConfirmsOrDie();
			ch.queueDelete(QUEUE_NAME);
			ch.close();
			conn.close();

			long endTime = System.currentTimeMillis();
			System.out.printf("Test took %.3fs\n",(float)(endTime - startTime)/1000);
		} catch (Throwable e) {
			System.out.println("foobar :(");
			System.out.print(e);
		}
	}
}