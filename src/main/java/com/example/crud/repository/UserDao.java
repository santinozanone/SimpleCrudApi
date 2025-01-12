package com.example.crud.repository;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.crud.model.User;

@Repository
public class UserDao {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public Optional<User> findById(int id) {
		try {
		User user = jdbcTemplate.queryForObject("select * from users where idusers = ?", 
				(resultSet,rowNum) -> {
					User newUser = new User(resultSet.getInt("idusers"), resultSet.getString("username"), resultSet.getString("email"), resultSet.getString("password"));
					return newUser;
				},id);
		return Optional.of(user);
		}catch (DataAccessException e) {
			return Optional.empty();
		}
	}
	
	public void create(User user) {
			jdbcTemplate.update("insert into users(username,email,password) values (?,?,?)",user.getUsername(),user.getEmail(),user.getPassword());
	}
	
	public void update(int id,User user) {
		jdbcTemplate.update("update users set username = ?, email = ?, password = ? where idusers = ? ",user.getUsername(),user.getEmail(),user.getPassword(),id);
	}
	
	public void delete(int id) {
		jdbcTemplate.update("delete from users where idusers = ?",id);
	}
	
}
