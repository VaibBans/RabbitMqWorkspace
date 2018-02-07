package com.cg.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class MessageReceiver implements Runnable {

	
	public MessageReceiver() {
		super();
	}
	static int msgCount = 10000;
	final static String QUEUE_NAME = "confirm-test";
	ConnectionFactory connectionFactory =  new ConnectionFactory();

	public static void main(String[] args) {
	
	}
	@Override
	public void run() {
		            try {
                // Setup
                Connection conn = connectionFactory.newConnection();
                Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, false, null);

                // Consume
                QueueingConsumer qc = new QueueingConsumer(ch);
                ch.basicConsume(QUEUE_NAME, true, qc);
                for (int i = 0; i < msgCount; ++i) {
                    qc.nextDelivery();
                }

                // Cleanup
                ch.close();
                conn.close();
            } catch (Throwable e) {
                System.out.println("Whoosh!");
                System.out.print(e);
            }
        }
		
	}

