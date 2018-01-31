package com.cg.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderTopic {

	private static final String EXCHANGE_NAME = "topic_logs";
	public static void main(String[] args) {
		Connection connection = null;
		Channel channel = null;
		try{
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
			String routingKey = getRoutingKey(args);
			String message = getMessage(args);
			channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes("UTF-8"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			if(connection!=null){
				try{
					connection.close();
				}catch(Exception ignore){}
			}
		}
	}
	public static String getMessage(String[] strings){
		if(strings.length<2)
			return "Hello World";
		return joinStrings(strings,"",1);
	}
	public static String getRoutingKey(String[] strings){
		if(strings.length<1)
			return "anonymous:info";
		return strings[0];
	}
	public static String joinStrings(String[] strings, String delimeter, int startIndex){
		int length = strings.length;
		if(length==0)
			return "";
		if(length<startIndex)
			return "";
		StringBuilder words = new StringBuilder(strings[startIndex]);
		for(int i=startIndex+1;i<length;i++){
			words.append(delimeter).append(strings[i]);
		}
		return words.toString();
	}
}
