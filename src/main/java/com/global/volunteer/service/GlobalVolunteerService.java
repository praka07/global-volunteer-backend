package com.global.volunteer.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.global.volunteer.model.User;

public interface GlobalVolunteerService {


	ResponseEntity<?> validateUserLogin(JSONObject loginPayload);

	ResponseEntity<?> registerUser(User newUserInfo);

	List<User> getAllUsers();

	ResponseEntity<?> updateUser(User updateUserInfo);

	ResponseEntity<?> updatePasswordById(String information);
	

}
