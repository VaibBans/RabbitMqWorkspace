package com.cg.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

  private final static String QUEUE_NAME = "hello3";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().expiration("2000").build();
       
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = "Hello World!";
    channel.basicPublish("", QUEUE_NAME, properties, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");
    channel.close();
    connection.close();
  }
}