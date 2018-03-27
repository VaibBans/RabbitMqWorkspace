package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService service;

	boolean flag = false;
	boolean temp = false;


	@RequestMapping(value = "/NewPage")
	public ModelAndView firstPage(Model map) {
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
		System.out.println("message send by user is- "+user.getMessage());
		System.out.println("Before Sending in controller");
		service.sendMessage(user.getMessage());
		System.out.println("Before receiving in controller");
//		service.receiveMessage();
		String message = /*service.receiveMessage()+*/" from user :"+user.getUserName();
		map.addAttribute("message",message);
		model.setViewName("Message");
		//		service.putMessage(message);
		return model;
	}
	//getMessage==>return value from cache
	/*	//puMessage==>add value to cache**/
}