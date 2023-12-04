package com.thecodeveal.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.thecodeveal.app.exception.UserNotFoundException;

import com.thecodeveal.app.model.User;
import com.thecodeveal.app.repo.AuthorityDetailsRepository;
import com.thecodeveal.app.repo.UserDetailsRepository;
import com.thecodeveal.app.service.CustomUserService;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class UserManageController {

	@Autowired
	CustomUserService customUserService;
	@Autowired
	AuthorityDetailsRepository authrepo;
	@Autowired
	UserDetailsRepository userrepo;

	@GetMapping("/usermanage")
	List<User> getAllUsers() {
		return userrepo.findAll();

	}

	@PutMapping("/usermanage/{id}")
	User updateUser(@RequestBody User newUser, @PathVariable Long id) {
		return userrepo.findById(id).map(user -> {
			user.setFirstname(newUser.getFirstname());
			user.setMiddlename(newUser.getMiddlename());
			user.setLastname(newUser.getLastname());
			user.setPersonalemail(newUser.getPersonalemail());
			user.setDateofbirth(newUser.getDateofbirth());
			user.setMobilenumber(newUser.getMobilenumber());
			return userrepo.save(user);
		}).orElseThrow(() -> new UserNotFoundException(id));
	}

	@DeleteMapping("/usermanage/{id}")
	String deleteTask(@PathVariable Long id) {
		if (!userrepo.existsById(id)) {
			throw new UserNotFoundException(id);
		}
		userrepo.deleteById(id);
		return "User with id " + id + " has been deleted success";
	}

}
