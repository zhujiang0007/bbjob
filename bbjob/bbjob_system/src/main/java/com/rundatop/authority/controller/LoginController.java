package com.rundatop.authority.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class LoginController {
	@RequestMapping("sys/login.html")
	public ModelAndView adminLogin(){
		return new ModelAndView("sys/login");
	}
	@RequestMapping("sys/index")
	public ModelAndView adminIndex(){
		return new ModelAndView("sys/index");
	}
}
