package com.thecodeveal.app.service;

import org.springframework.beans.factory.annotation.Autowired
;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thecodeveal.app.model.User;
import com.thecodeveal.app.repo.UserDetailsRepository;

@Service
public class CustomUserService implements UserDetailsService{
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userDetailsRepository.findByUsername(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("user not found");
		}

		return user;
		
	}
	
	
	public UserDetails updateDetails(User user)
	{
		return userDetailsRepository.save(user);
	}
	
	public User getDetails(String username)
	{
		return userDetailsRepository.findByUsername(username);
	}
	
	
}
