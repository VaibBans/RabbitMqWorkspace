package com.cg.guava.cache.cassandra;


import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessagePublisher {

  private final static String QUEUE_NAME = "cachelogs";

  public static void main(String[] argv) throws Exception {
    Scanner sc = new Scanner(System.in);
	ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    
    System.out.println("Enter your message");
    String message = sc.nextLine();
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
    sc.close();
  }
}