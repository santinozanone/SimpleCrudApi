package com.example.crud.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.dto.UserDto;
import com.example.crud.exception.UserNotFoundException;
import com.example.crud.model.User;
import com.example.crud.repository.UserDao;

@Service
public class UserService {
	private static final Logger logger = LogManager.getLogger(UserService.class);

	private UserDao userDao;
	
	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	public Optional<UserDto> getUserById(int id) {
		logger.info("entering getUserById() with Id: '{}' ",id);
		Optional<User> userResult =  userDao.findById(id);
		if(userResult.isPresent()) {
			return Optional.of(new UserDto(userResult.get().getEmail(),userResult.get().getUsername()));
		}
		logger.info("Sucessfully retrieved user with Id: '{}' ",id);
		return Optional.empty();	
		
	}
	
	public void createUser(User user) {
		logger.info("entering createUser() with Id: '{}' ",user.getId());
		userDao.create(user);
		logger.info("Sucessfully created user with Id: '{}' ",user.getId());
	}
	
	public void updateUser(int id,User newUser) {	
		logger.info("entering updateUser() with Id: '{}' ",id);
		userDao.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		userDao.update(id, newUser);
		logger.info("Sucessfully updated user with Id: '{}' ",id);
	}
	
	public void deleteUser(int id) {
		logger.info("entering deleteUser() with Id: '{}' ",id);
		userDao.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		userDao.delete(id);
		logger.info("Sucessfully deleted user with Id: '{}' ",id);
	}
	
	
}
