package com.rabbit.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbit.model.Message;

/**
 * @author Justin Mathew
 *
 * Created On 15-Mar-2018
 */
@Service
public class RabbitService {
	 @Autowired
	 private RabbitTemplate rabbitTemplate;

	 public void send(String routingKey,Message message) {
		 rabbitTemplate.convertAndSend(routingKey, message);
	 } 
	  
}
