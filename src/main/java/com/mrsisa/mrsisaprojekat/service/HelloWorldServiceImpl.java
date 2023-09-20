package com.mrsisa.mrsisaprojekat.service;

import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.HelloWorld;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {
	
	@Override
	public HelloWorld getHelloWorld() {
		return new HelloWorld();
	}

}
