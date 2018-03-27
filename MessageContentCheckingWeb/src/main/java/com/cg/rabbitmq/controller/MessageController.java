package com.cg.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cg.rabbitmq.bean.MessageBean;
import com.cg.rabbitmq.service.MessageService;

import ch.qos.logback.core.util.SystemInfo;

@RestController
public class MessageController {

	@Autowired
	MessageService service;

	ModelAndView model = new ModelAndView();
	int count;
	
	@RequestMapping(value = "/Login")
	public ModelAndView viewPage(){
		model.setViewName("Login");
		model.addObject("user", new MessageBean());
		return model;
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ModelAndView loginIntoAccount(@ModelAttribute("user") MessageBean bean){
		if(service.validateUserName(bean.getUserName())&&service.validatePassword(bean.getPassword()))
		{
			model.addObject("user", bean);
			model.setViewName("Message");
		}
		return model;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ModelAndView sendMessage(@ModelAttribute("user") MessageBean messageBean){
		String msg;
		int num;
		try {
			service.sendMessage(messageBean.getMessage());
			count = service.checkContent(messageBean.getMessage());
			if(count==1){
				msg = "Message Sent Successfully ";
				model.addObject("message", messageBean.getMessage());
				model.setViewName("Message");
			}

			else{
				msg = "Message type is other than characters";
				num = service.numberOfMessages();
				if(num<=51){
					model.addObject("message", msg);
					model.setViewName("Error");
				}
				else{
					msg = "Your chances are over. Not able to send message";
					System.out.println("Clearing the content of file");
					service.clearFileContent();
					model.addObject("message", msg);
					model.setViewName("ErrorPage");
				}
			}
//			clearing the content of file
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;	
	}

	@RequestMapping(value = "/Message")
	public ModelAndView resendMessage(@ModelAttribute("users") MessageBean bean){
		model.addObject("user", bean);
		model.setViewName("Message");
		return model;
	}
	
	@RequestMapping(value = "/Received")
	public ModelAndView receiveMessage(){	
		model.setViewName("Received");
		return model;
	}
	
	@RequestMapping(value = "/rec")
	public ModelAndView receivedMessage(){
		boolean val;
		try{
		service.receiveMessage();
		Thread.sleep(4000);
		val = service.checkContentInFile();
		model.addObject("bool", val);
		model.setViewName("Received");		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return model;
	}
}