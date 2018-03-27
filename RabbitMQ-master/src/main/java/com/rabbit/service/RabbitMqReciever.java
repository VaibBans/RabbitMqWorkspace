package com.rabbit.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import com.rabbit.configs.RabbitConfigs;
import com.rabbit.custom.annotations.CustomDirectListener;
import com.rabbit.message.cache.MessageCacheStorage;
import com.rabbit.model.Message;
/**
 * @author Justin Mathew
 *
 * Created On 15-Mar-2018
 */
@Service
public class RabbitMqReciever {
	@Autowired
	MessageCacheStorage messageStore;
	
	  @CustomDirectListener
	  public void receive(Message response, @Headers Map<String, Object> headers) throws IOException {
	      messageStore.pushMessage(RabbitConfigs.ROURING_KEY_1_1, response);
	  }
}
