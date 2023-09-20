package com.mrsisa.mrsisaprojekat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.model.HelloWorld;
import com.mrsisa.mrsisaprojekat.service.HelloWorldService;

@RestController
@RequestMapping("/api/helloworld")
public class HelloWorldController {

	@Autowired
	private HelloWorldService helloWorldService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HelloWorld> getHelloWorld() {
		HelloWorld helloWorld = helloWorldService.getHelloWorld();
		return new ResponseEntity<HelloWorld>(helloWorld, HttpStatus.OK);
	}
	
}
