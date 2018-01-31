package com.cg.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderWorkQueues {
	private final static String TASK_QUEUE_NAME = "hello";
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		com.rabbitmq.client.Channel channel = connection.createChannel();
		channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
		String message = getMessage(args);
		channel.basicPublish("", "hello",null,message.getBytes("UTF-8"));
		System.out.println("[x] Sent '"+ message+"'" );
		
		channel.close();
		connection.close();
	}
	public static String getMessage(String[] strings){
		if(strings.length<1){
			/*System.out.println("Enter the message: ");
			Scanner sc = new Scanner(System.in);
			String msg = sc.nextLine();*/
			return "HI";
		}
		return joinStrings(strings, " ");
	}
	public static String joinStrings(String[] strings, String delimiter){
		int length = strings.length;
		if(length == 0)
			return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for(int i=1;i<length;i++){
			words.append(delimiter).append(strings[i]);
		}
		//System.out.println("In words: " +words.toString());
		return words.toString();
	}
}
