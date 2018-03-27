package com.rabbit.configs;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@Configuration
public class RabbitConsumerConfig {
	  @Autowired
	  ConnectionFactory connectionFactory;
	 
	  @Bean("OrderListenerFactory")
	  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
	    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	    factory.setConnectionFactory(this.connectionFactory);
	    factory.setConcurrentConsumers(1);
	    factory.setMaxConcurrentConsumers(2);
	    factory.setErrorHandler(errorHandler());
	    return factory;
	  }
	  @Bean
	  public ErrorHandler errorHandler() {
	      return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
	  }
	  
	  public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
	    @Override
	    public boolean isFatal(Throwable t) {
	      if (t instanceof ListenerExecutionFailedException) {
	        ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
	      }
	      return true;
	    }
	  }
}