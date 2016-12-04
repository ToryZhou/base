/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * 2016年12月3日 上午9:28:17
 */
package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.example.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("user")
	public String getUser(){
		System.out.println("in the controller  ---====");
		return userService.getUser();
	}


	@PostMapping("user/{name}")
	public String addUser(@PathVariable String name){
		System.out.println(name);
		return userService.getUser();
	}
}
