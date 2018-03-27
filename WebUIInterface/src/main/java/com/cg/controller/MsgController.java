package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cg.bean.MsgBean;
import com.cg.dao.MsgDao;

@Controller
public class MsgController {

	@Autowired
	MsgDao dao;
	
	ModelAndView model = new ModelAndView();
	
	@RequestMapping(value = "/firstpage")
	public ModelAndView send(){
	
		model.addObject("msg", new MsgBean());
		model.setViewName("firstpage");
		return model;
	}
	@RequestMapping(value = "/sendmsg")
	public ModelAndView success(@ModelAttribute("msg") MsgBean bean) throws Exception{
	
		dao.sendMessage(bean.getMessage());
		dao.receiveMessage(bean.getMessage());
		model.setViewName("success");
		return model;
	}
}
