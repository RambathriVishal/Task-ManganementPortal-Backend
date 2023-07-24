package com.thecodeveal.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thecodeveal.app.model.Register;

import com.thecodeveal.app.repo.RegisterRepo;

@RestController
@CrossOrigin("*")
public class RegisterController {
	
	@Autowired
	private RegisterRepo registerrepo;
	

	@PostMapping("/register")
	Register newRegister(@RequestBody Register newRegi) {
		return registerrepo.save(newRegi);
	}

}
