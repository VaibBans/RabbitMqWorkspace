package com.cg.rabbitmq;

import java.io.IOException;

public class TestMain {

	public static void main(String[] args)
			throws IOException, InterruptedException
	{		
		MessageReceiver messageReceiver = new MessageReceiver();
		Publisher publisher = new Publisher();
		// Consume msgCount messages.
		(new Thread(messageReceiver)).start();
		// Publish msgCount messages and wait for confirms.
		(new Thread(publisher)).start();
	}

}