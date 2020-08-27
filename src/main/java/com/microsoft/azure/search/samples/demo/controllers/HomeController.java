package com.microsoft.azure.search.samples.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller 
public class HomeController {
    
    @GetMapping("/")
	public String index() {		
		return "home";
	}
}