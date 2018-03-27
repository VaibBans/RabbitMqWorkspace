package com.cg.rabbitmq;

import java.util.Scanner;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

	private static final String EXCHANGE_NAME  = "direct_logs";
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.DIRECT);
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the message");
		String msgFile = sc.nextLine();
		String severity =  getSeverity(args);
		//String message = getMessage(args);
		channel.basicPublish(EXCHANGE_NAME, severity,null,msgFile.getBytes("UTF-8"));
		System.out.println("Message Sent: "+"Severity: "+severity+" :"+msgFile);
		System.out.println("Value of get mEssage function is: "+EmitLog.getMessage(args));
		channel.close();
		connection.close();
		sc.close();
	}
	public static String getMessage(String[] strings) {
		if(strings.length<2) {
			//System.out.println("Length of string is :"+strings.length);
			return "Hello world";
		}
		return joinStrings(strings," ",1);
	}
	public static String getSeverity(String[] strings) {
		if(strings.length<1)
			return ":info";
		return "Hi all in severity";
	}
	public static String joinStrings(String[] strings,String delimiter, int startIndex) {
		int length = strings.length;
		/*String[] msgStrings = new String[100];
		for(int j=0;j<strings.length();j++) {
			msgStrings[j] = strings;
		}*/
		if(length==0)
			return "";
		if(length<startIndex)
			return "";
		StringBuilder words = new StringBuilder(strings[startIndex]);
		for(int i=startIndex+1;i<length;i++) {
			words.append(delimiter).append(strings);
		}
		return words.toString();
	}

}
