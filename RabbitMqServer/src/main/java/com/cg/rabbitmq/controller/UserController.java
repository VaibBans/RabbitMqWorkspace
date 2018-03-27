package com.cg.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.rabbitmq.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService service ;
	
	@RequestMapping("/submit")
	public String displayMessage(@RequestParam("username") String userName,@RequestParam("password") String password,Model model) throws Exception{
		int check = 0;
		int count = 0;
		String message = "";
		String redirect = "";
		check = service.validateId(userName);
		count = service.validatePassword(password);
		if(check==1&&count==1){
			message = service.sendMessage();
			redirect = "success";
			model.addAttribute("message",message);
		}
		return redirect;
	}
}