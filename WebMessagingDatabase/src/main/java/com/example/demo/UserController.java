package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.JAVACassandraConnectivity.CassandraMethods;
import com.example.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService service;
	
	CassandraMethods cassandraMethods = new CassandraMethods();

	boolean flag = false;
	boolean temp = false;
	
	@RequestMapping(value = "/NewPage")
	public ModelAndView firstPage(Model map) {
		cassandraMethods.createSession();
		cassandraMethods.createKeyspace();
		cassandraMethods.createTable();
		ModelAndView model = new ModelAndView();
		model.setViewName("NewPage");
		map.addAttribute("userLogin", new UserBean());
		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginPage(@ModelAttribute("userLogin") UserBean user, BindingResult result,Model map) {
		ModelAndView model = new ModelAndView();
		flag = service.validateUsername(user.getUserName());
		temp = service.validatePassword(user.getPassword());
		if (flag == true && temp == true) {
			model.setViewName("SuccessPage");
		}
		else{
			model.setViewName("ErrorPage");
			map.addAttribute("message", "Not a valid user");
		}
		return model;
	}

	@RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
	public ModelAndView sendMessage(@ModelAttribute("userLogin") UserBean user, Model map,BindingResult result) throws Exception{
		ModelAndView model = new ModelAndView();
		service.sendMessage(user.getMessage());
		String message = service.receiveMessage(user.getMessage()) + " from user :"+user.getUserName();
		CassandraMethods cassandraMethods = new CassandraMethods();
		cassandraMethods.createSession();
		cassandraMethods.createKeyspace();
		cassandraMethods.useKeyspace();
		cassandraMethods.createTable();
		map.addAttribute("message",message);
		model.setViewName("Message");
//		service.putMessage(message);
		return model;
	}
	//getMessage==>return value from cache
/*	//puMessage==>add value to cache**/
	}


