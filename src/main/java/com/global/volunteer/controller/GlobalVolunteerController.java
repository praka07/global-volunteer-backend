package com.global.volunteer.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.global.volunteer.model.User;
import com.global.volunteer.service.IGlobalVolunteerService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GlobalVolunteerController {
	
	@Autowired
	IGlobalVolunteerService serviceObj;
	
	@PostMapping("/validateuserlogin")
	public ResponseEntity<?> validateUserLogin(@RequestBody String requestPayload){
		JSONObject loginPayload = new JSONObject(requestPayload);
		if("".equalsIgnoreCase(loginPayload.getString("username")) || null == loginPayload.getString("username")) {
			return ResponseEntity.badRequest().body("{ \"message\" : \"username cannot be empty\"}");
			
		}else if ("".equalsIgnoreCase(loginPayload.getString("password")) || null == loginPayload.getString("password")) {
			return ResponseEntity.badRequest().body("{ \"message\" : \"password cannot be empty\"}");
			
		}else {
			return serviceObj.validateUserLogin(loginPayload);
		}
		
		
	}
	
	@PostMapping("/registeruser")
	public ResponseEntity<?> registerUser(@RequestBody User newUserInfo) {
		return serviceObj.registerUser(newUserInfo);
	}
	
	@GetMapping("/getalluser")
	public List<User> getAllUsers() {
		return serviceObj.getAllUsers();

	}
	
	@PutMapping("/updateuserdetail")
	public ResponseEntity<?> updateUser(@RequestBody User updateUserInfo) {
		return serviceObj.updateUser(updateUserInfo);
	}

	@PostMapping("/updatepassword")
	public ResponseEntity<?> updatePasswordById(@RequestBody String information) {
		return serviceObj.updatePasswordById(information);
	}
	
	

}
