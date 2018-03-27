package com.cg.rabbitmq.producer;

public class ProducerMain {

	public static void main(String[] args) throws Exception {
		Producer.sendMsg("Hello");
	}
}