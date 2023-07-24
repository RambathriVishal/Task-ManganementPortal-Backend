package com.thecodeveal.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecodeveal.app.model.Authority;


import com.thecodeveal.app.model.User;

import com.thecodeveal.app.repo.UserDetailsRepository;
import com.thecodeveal.app.service.AuthorityService;
import com.thecodeveal.app.service.CustomUserService;


@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class AppController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	CustomUserService customUserService;

	@Autowired
	AuthorityService authorityService;
	

	@PostMapping("/changepassword")
	public void changePassword(@RequestBody String u) throws JSONException,Exception
	{
		
		JSONObject jsonObject= new JSONObject(u);
		
		String username = (String) jsonObject.get("username");
		String oldpassword = (String) jsonObject.get("oldpassword");
		String newpassword = (String) jsonObject.get("newpassword");
		
			
			User user=userDetailsRepository.findByUsername(username);
			
			if(user==null)
			{
				throw new Exception("invalid email");
			}
		
		
	      String password =user.getPassword();
	      
	       boolean result = (passwordEncoder.matches(oldpassword,password));
		
	      	if(!result)
	      	{
	      		throw new Exception("invalid email");
	      	}
	      	
	      	
	      	user.setPassword(passwordEncoder.encode(newpassword));
	      	
	      	userDetailsRepository.save(user);
	      	
	      	System.out.println(result);
		
			System.out.println(username+" "+oldpassword+" "+newpassword);
	}
	
	
	@PostMapping("/addCandidate")
	public boolean addCandidate(@RequestBody String u) throws JSONException
	{
		JSONObject jsonObject= new JSONObject(u);
		
			String email = (String) jsonObject.get("cemail");
			String password = (String)  jsonObject.get("cpassword");
			String name = (String) jsonObject.get("cname");
		
			String role = (String) jsonObject.get("crole");

		
		
		 
			
			  List<Authority>authorityList=new ArrayList<>();
				
				authorityList.addAll(authorityService.findAuthority((long)1));
				
				
				if((userDetailsRepository.findByUsername(email))!=null)
				{
					return false;
				}
				
				User user =new User();
				
				
				
				user.setRole(role);
				
				user.setUsername(email);			
				user.setFirstname(name);
				user.setPassword(passwordEncoder.encode(password));				
				user.setAuthorites(authorityList);	
				userDetailsRepository.save(user);
				return true;
			  
		
	}
	
			
		
	@GetMapping("/userdetails/{username}")
	@ResponseBody
	public String getDetails(@PathVariable("username") String email) throws JsonProcessingException{
	  ObjectMapper mapper = new ObjectMapper();
	  User u = customUserService.getDetails(email);
	  return mapper.writeValueAsString(u);
	}
	
	
	@PostMapping("/details/{username}")
	public boolean updateDetails(@RequestBody User user , @PathVariable("username") String email )
	{
		
		
		
		
		User u = userDetailsRepository.findByUsername(email);
		
		u.setFirstname(user.getFirstname());
		u.setMiddlename(user.getMiddlename());
		u.setLastname(user.getLastname());
		u.setMobilenumber(user.getMobilenumber());
		u.setDateofbirth(user.getDateofbirth());
		u.setPersonalemail(user.getPersonalemail());
		customUserService.updateDetails(u);
		
		
		return true;
	}

	
	
	}
	

