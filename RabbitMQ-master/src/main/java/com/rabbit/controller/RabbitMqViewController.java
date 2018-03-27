package com.rabbit.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rabbit.configs.RabbitConfigs;
import com.rabbit.message.cache.MessageCacheStorage;
import com.rabbit.model.Message;
import com.rabbit.service.RabbitService;

/**
 * @author Justin Mathew
 *
 * Created On 15-Mar-2018
 */
@Controller
public class RabbitMqViewController {

	@Autowired
	RabbitService rabbitService;
	@Autowired
	MessageCacheStorage messageStore;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView test(@RequestParam(required=false)String test)
	{
		ModelAndView model=new ModelAndView("index");
		model.addObject("messages", messageStore.getMessage(RabbitConfigs.ROURING_KEY_1_1));
		return model;
	}
     
	@RequestMapping(value = "/post-message", method = RequestMethod.POST)
	public ModelAndView postMessage(Message message,HttpServletRequest request, HttpServletResponse response)
	{
		message.setCreatedOn(new Date());
		rabbitService.send(RabbitConfigs.ROURING_KEY_1_1, message);
	
	    return new ModelAndView("redirect:/home");
	}
    
}
