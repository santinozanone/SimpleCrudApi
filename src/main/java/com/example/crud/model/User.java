package com.example.crud.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class User {
	
	@NotNull(message="id cannot be null")
	@Min(message="minimum value is 1",value=1)
	@Max(message="maximum value is 500",value=500)
	private Integer id;
	
	@NotBlank(message="username cannot be blank")
	private String username;
	
	@NotBlank(message="email cannot be blank")
	@Email(message="invalid email format")
	private String email;
	
	@NotBlank(message="password cannot be blank")
	private String password;
	
	public User() {
		
	}
	
	
	public User(Integer id, String username, String email, String password) {		
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	
	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	

	public void setId(Integer id) {
		this.id = id;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
