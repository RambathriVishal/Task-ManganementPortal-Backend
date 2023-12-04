package com.example.controller;

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
import com.example.model.Authority;


import com.example.model.User;

import com.example.repo.UserDetailsRepository;
import com.example.app.service.AuthorityService;
import com.example.app.service.CustomUserService;


@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class AppController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;

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
		
			String firstname = (String) jsonObject.get("firstname");
			String lastname = (String) jsonObject.get("lastname");
			String street = (String) jsonObject.get("street");
			String address = (String) jsonObject.get("address");
			String city = (String) jsonObject.get("city");
			String state = (String) jsonObject.get("state");
			String email = (String)  jsonObject.get("email");
			String phone = (String) jsonObject.get("phone");
		
			

		
		
		 
			
			  List<Authority>authorityList=new ArrayList<>();
				
				authorityList.addAll(authorityService.findAuthority((long)1));
				
				
				if((CustomerRepository.findByUsername(email))!=null)
				{
					return false;
				}
				
				Customer customer =new Customer();
				
				

				
				customer.setFirstame(firstname);	
				customer.setLastname(lastname);
				customer.setStreet(street);
				customer.setAddress(address);
				customer.setCity(city);
				customer.setState(state);
				customer.setEmail(email);
				customer.setPhone(phone);
				
				
				
				
				
				
				
				CustomerRepository.save(customer);
				return true;
			  
		
	}
	
			
		
	@GetMapping("/userdetails/{username}")
	@ResponseBody
	public String getDetails(@PathVariable("username") String email) throws JsonProcessingException{
	  ObjectMapper mapper = new ObjectMapper();
	  User u = customUserService.getDetails(email);
	  return mapper.writeValueAsString(u);
	}
	
	
	@PutMapping("/details/{username}")
	public boolean updateDetails(@RequestBody Customer customer , @PathVariable("username") String email )
	{
		
		
		
		
		Customer c = CustomerRepository.findByUsername(email);
		
		c.setFirstname(user.getFirstname());
		c.setLastename(user.getLastname());
		c.setStreet(customer.getStreet());
		c.setAddress(user.getAddress());
		c.setCity(user.getCity());
		c.setState(user.getState());
		c.setEmail(customer.getEmail());
		c.setPhone(customer.getPhone());
		customerService.updateDetails(c);
		
		
		return true;
	}

	
	
	}
	

@GetMapping("/customer")
	List<User> getAllCustomer() {
		return customerRepository.findAll();

	}

	@DeleteMapping("/customer/{id}")
	String deleteTask(@PathVariable Long id) {
		if (!customerRepository.existsById(id)) {
			throw new UserNotFoundException(id);
		}
		customerRepository.deleteById(id);
		return "Customer with id " + id + " has been deleted success";
	}