package com.example.crud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.dto.UserDto;
import com.example.crud.exception.UserNotFoundException;
import com.example.crud.model.User;
import com.example.crud.repository.UserDao;

@Service
public class UserService {

	private UserDao userDao;
	
	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	public Optional<UserDto> getUserById(int id) {
		Optional<User> userResult =  userDao.findById(id);
		if(userResult.isPresent()) {
			return Optional.of(new UserDto(userResult.get().getEmail(),userResult.get().getUsername()));
		}
		return Optional.empty();	
	}
	
	public void createUser(User user) {
		userDao.create(user);
	}
	
	public void updateUser(int id,User newUser) {		
		userDao.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		userDao.update(id, newUser);
	}
	
	public void deleteUser(int id) {
		userDao.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		userDao.delete(id);
	}
	
	
}
