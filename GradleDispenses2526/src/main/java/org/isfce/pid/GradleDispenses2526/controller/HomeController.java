package org.isfce.pid.GradleDispenses2526.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@GetMapping("/")
	String home(Model model){
		model.addAttribute("prof", "VO");
		return "home";
	}
	
}
