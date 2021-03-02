package com.global.volunteer.dao;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.global.volunteer.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class IGlobalVolunteerServiceDao implements GlobalVolunteerServiceDao {
	
    @Autowired
    JdbcTemplate jdbcTemplate;


	@Override
	public ResponseEntity<?> validateUserLogin(JSONObject loginPayload) {
		String query = "select * from users where emailid ='" + loginPayload.getString("username") + "' and password='"
				+ loginPayload.getString("password") + "'" + " and active=1";
		log.info("query to validate user {} ", query);
		
		try {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<User> userResult = jdbcTemplate.query(query, new BeanPropertyRowMapper(User.class));
			if (userResult.isEmpty()) {
				return ResponseEntity.badRequest().body("{ \"message\" : \"invalid user\"}");
			} else {
				return ResponseEntity.ok().body(userResult.get(0));
			}
			
		} catch (Exception e) {
			log.info("error wile validate login", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}




}
