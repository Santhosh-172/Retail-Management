package com.inventory.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {

	@GetMapping("hello")
	public String hello() {
		return "Hello from Inventory ii";
	}
}
