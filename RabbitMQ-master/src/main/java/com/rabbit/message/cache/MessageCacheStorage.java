package com.rabbit.message.cache;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.internal.util.logging.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.rabbit.model.Message;
@Component
public class MessageCacheStorage {
	  @Autowired
	  private Cache<String, List<Message>> cache;

	  public void pushMessage(String queue,Message message)
	  {
		  List<Message>previousMessages=getMessage(queue);
		  if(previousMessages==null)
		  {
			  previousMessages=new ArrayList<>(); 
		  }
		  previousMessages.add(message);
		  cache.put(queue, previousMessages);
	  }
	  
	  public List<Message> getMessage(String queue)
	  {
  		 return cache.getIfPresent(queue);
	  }
	  
}