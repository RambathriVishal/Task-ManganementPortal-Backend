package com.thecodeveal.app.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thecodeveal.app.config.JWTTokenHelper;
import com.thecodeveal.app.model.User;
import com.thecodeveal.app.request.AuthenticationRequest;
import com.thecodeveal.app.response.LoginResponse;
import com.thecodeveal.app.response.UserInfo;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticationController {
	
	
			@Autowired
			private AuthenticationManager AuthenticationManager;
			
			@Autowired
			private JWTTokenHelper jwtTokenHelper;
			
			@Autowired
			private UserDetailsService userDetailsService;
	
	
		
			@PostMapping("/auth/login")
			public ResponseEntity<?>login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException,NoSuchAlgorithmException	
			{
				
				System.out.println(authenticationRequest.getUserName()+" "+authenticationRequest.getPassword());
				
				final Authentication authentication =AuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
				
				
				
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				User user=(User)authentication.getPrincipal();
				
				System.out.println(user.getAuthorites());
				
				String jwt=jwtTokenHelper.generateToken(user.getUsername());
				
				System.out.println(jwt);
				
				LoginResponse response=new LoginResponse();
				
				response.setToken(jwt);
				
				
				return ResponseEntity.ok(response);
				
			}
			
			
			@GetMapping("/auth/userinfo")
			public Boolean getUserInfo(Principal user) throws Exception
			{
				if(user==null)
					{
					   throw new Exception("user not found");
					}
				
				 User userObj=(User) userDetailsService.loadUserByUsername(user.getName());
				 
				 UserInfo userInfo=new UserInfo();
				 
				 userInfo.setUsername(userObj.getUsername());
				 
				 userInfo.setUserRoles(userObj.getAuthorites().toArray());
				 
				 System.out.println(userObj);
				 
				 boolean ans=true;
				 
				 if(userObj==null)
				 {
					 ans=false;
				 }
				 
				 return ans;
			}
			
			
			
			
			
}
