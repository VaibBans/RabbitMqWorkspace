package com.rabbit.configs;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author Justin Mathew
 *
 * Created On 15-Mar-2018
 */
@Configuration
public class RabbitProducerConfig {
 
	
	 @Autowired
	 ConnectionFactory connectionFactory;
	      
	 @Bean
	 public AmqpAdmin amqpAdmin() {
	 return new RabbitAdmin(connectionFactory);
	 }
	 @Bean
	 public RabbitTemplate rabbitTemplate() {
	 return new RabbitTemplate(connectionFactory);
	 }
	 @Bean
	 public Queue myQueue() {
	 return new Queue(RabbitConfigs.ROURING_KEY_1_1);
	 }
}
