package com.cg.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Sender {

	private static final String QUEUE1 = "check1";
	private static final String EXCHANGE1 = "checkex1";
	private final static String DEAD_LETTER_QUEUE = "check_dead_letter_queue1";
	private final static String DEAD_LETTER_EXCHANGE = "check_dead_letter_exchange1";
	
	public static void main(String[] args) throws Exception{
		Logger logger = LoggerFactory.getLogger(Sender.class);
		Scanner sc = new Scanner(System.in);
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
		
		channel.queueDeclare(DEAD_LETTER_QUEUE, false, false, false,null );
		channel.queueBind(DEAD_LETTER_QUEUE, DEAD_LETTER_EXCHANGE, "");
		
		System.out.println("Enter the message");
		String message = sc.next();
		channel.basicPublish(EXCHANGE1, QUEUE1, true, null, message.getBytes());
//		channel.basicPublish(EXCHANGE1,QUEUE1, null,message.getBytes());
		logger.info("Message Sent Successfully");
		sc.close();
	}
}