package com.example.mentor.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping({ "/", "/home" })
	public String index() {
		return "index";
	}
	
	@GetMapping("/famous")
	public String restaurant() {
		return "map/restaurant";
	}
	
	@GetMapping("/smarteditor")
	public String userBoard() {
		return "userboard/write";
	}
	
	@GetMapping("/youtube")
	public String youtube() {
		return "youtube";
	}
}
