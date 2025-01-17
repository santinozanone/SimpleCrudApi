package com.example.crud.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.dto.UserDto;
import com.example.crud.model.User;
import com.example.crud.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class UserController {
	private static final Logger logger = LogManager.getLogger(UserController.class);

	private UserService service;
	
	@Autowired
	public UserController(UserService service) {
		this.service = service;    
	}
	
	
	@GetMapping("/users/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id){
		logger.info("GET request to getUserById() with id: '{}' ",id);
		Optional<UserDto> userResult = service.getUserById(id);	
		return userResult.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
		
	}
	
	@PostMapping("/users")
	public ResponseEntity<String> createUser(@Valid @ModelAttribute User user){		
		logger.info("POST request to createUser() with user:{} ",user);
		service.createUser(user);
		return ResponseEntity.ok("User created correctly");
	}
	
	/*
	 * when using @RequestBody (expecting a json from the body) the user class must have a default constructor in
	 * order to work properly, 
	 * 
	 * when using @ModelAttribute the class doesnt need to have a default constructor or setters but its
	 * mandatory to add a BindingResult next to it in the method parameters in order to work properly.
	 * 
	 * if the class we are working on (in this case User class) must use @ModelAttribute and @RequestBody
	 * then we need to add a default constructor and setters, at least that is the only way i found,
	 * */
	
	
	@PutMapping("/users/{id}")
	public ResponseEntity<String> updateUser(@Valid @RequestBody User user,BindingResult result,@PathVariable("id") int id ){	
		logger.info("PUT request to updateUser() with Id: '{}' ",id);
		service.updateUser(id, user);
		return ResponseEntity.ok("User updated correctly " + user);
	}
	

	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int id){
		logger.info("DELETE request to deleteUser() with Id: '{}' ",id);
		service.deleteUser(id);
		return ResponseEntity.ok("user deleted correctly");
	}
	
	
}
