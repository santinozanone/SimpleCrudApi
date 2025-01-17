package com.example.crud.repository;

import java.util.Optional;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.crud.model.User;

@Repository
public class UserDao {
	private static final Logger logger = LogManager.getLogger(UserDao.class);
	private static final Marker SQL_MARKER = MarkerManager.getMarker("SQL");

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public Optional<User> findById(int id) {
		try {
			String query = "select * from users where idusers = ?";
			logger.info(SQL_MARKER,"select * from users where idusers = {}",id);
		User user = jdbcTemplate.queryForObject(query, 
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
        logger.info("Creating user with: {}", user);
		jdbcTemplate.update("insert into users(username,email,password) values (?,?,?)",user.getUsername(),user.getEmail(),user.getPassword());
	}
	
	public void update(int id,User user) {
		logger.info("Updating user with id:{} with {}",id, user);
		jdbcTemplate.update("update users set username = ?, email = ?, password = ? where idusers = ? ",user.getUsername(),user.getEmail(),user.getPassword(),id);
	}
	
	public void delete(int id) {
		logger.info("Deleting user with id: {}", id);
		jdbcTemplate.update("delete from users where idusers = ?",id);
	}
	
}
