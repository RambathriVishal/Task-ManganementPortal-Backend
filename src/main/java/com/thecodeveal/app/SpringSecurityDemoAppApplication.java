package com.thecodeveal.app;



import java.util.ArrayList
;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.thecodeveal.app.model.Authority;

import com.thecodeveal.app.model.User;
import com.thecodeveal.app.repo.AuthorityDetailsRepository;
import com.thecodeveal.app.repo.UserDetailsRepository;
import com.thecodeveal.app.service.AuthorityService;

@SpringBootApplication
public class SpringSecurityDemoAppApplication {

	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthorityDetailsRepository authorityDetailsRepository;
	
	@Autowired
	AuthorityService authorityService;
	
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoAppApplication.class, args);
	}
	
	@PostConstruct
	protected void init()
	{

		
		
		if(userDetailsRepository.findByUsername("saicharanpoleboina@gmail.com")==null)
		{
			
			System.out.println("ok");
			
			Authority authority =new Authority((long)2, "USER");
			
			authorityService.saveAuthority(authority);
			

			
			Authority authority3 =new Authority((long)5, "ADMIN");
			
			authorityService.saveAuthority(authority3);
			
			
			List<Authority>authorityList=new ArrayList<>();
			
			authorityList.addAll(authorityService.findAuthority((long) 1));
			
			User user =new User();
		
			user.setUsername("saicharanpoleboina@gmail.com");
			
			user.setPassword(passwordEncoder.encode("CHara!4545"));
			
			user.setAuthorites(authorityList);
			
			
			
			userDetailsRepository.save(user);
			
			System.out.println(user.getUsername() +" "+user.getPassword());
			
		}
			
					
		
		
		if(userDetailsRepository.findByUsername("saicharanpoleboina2@gmail.com")==null)
		{
			List<Authority>authorityList=new ArrayList<>();
			
			authorityList.addAll(authorityService.findAuthority((long) 1));
			
			User user =new User();
		
			user.setUsername("saicharanpoleboina2@gmail.com");
			
			user.setPassword(passwordEncoder.encode("CHara!4545"));
			
			user.setAuthorites(authorityList);
			
			
			
			userDetailsRepository.save(user);
			
		System.out.println(user.getUsername() +" "+user.getPassword());
		}
		
		if(userDetailsRepository.findByUsername("vishalrambathri11@gmail.com")==null)
		{
			List<Authority>authorityList=new ArrayList<>();
			
			authorityList.addAll(authorityService.findAuthority((long) 2));
			
			User user =new User();
		
			user.setUsername("vishalrambathri11@gmail.com");
			
			user.setPassword(passwordEncoder.encode("VIs@123"));
			
			user.setAuthorites(authorityList);
			
			
			
					userDetailsRepository.save(user);
			
				System.out.println(user.getUsername() +" "+user.getPassword());
		}


		

	}

   
    
    
}
