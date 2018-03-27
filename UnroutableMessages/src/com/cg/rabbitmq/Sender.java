package com.cg.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;

public class Sender implements ReturnListener {

	private static final String UnroutableExchange = "unroutableExchange";

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		System.out.println("Enter your message");
		String message = sc.nextLine();
		boolean isMandatory = true;
		channel.exchangeDeclare(UnroutableExchange, "direct",false,false,null);
		channel.basicPublish(UnroutableExchange, "", isMandatory,null,message.getBytes());
		channel.addReturnListener(new Sender());
		System.out.println("Message sent "+message);
		sc.close();
	}
	@Override
	public void handleReturn(int arg0, String arg1, String arg2, String arg3, BasicProperties arg4, byte[] arg5)
			throws IOException {
		System.out.println("Message returned "+ new String(arg5,"UTF-8"));
	}
}