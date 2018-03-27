package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

@Component
public class PersonRemovalListener implements RemovalListener<String,Person>{

	Logger logger = LoggerFactory.getLogger(PersonRemovalListener.class);
	
	public void onRemoval(RemovalNotification notification) {
		logger.info("Person associated with the key("+
				notification.getKey()+ ") is removed.");
	}
}