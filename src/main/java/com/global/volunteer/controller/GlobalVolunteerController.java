package com.global.volunteer.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.global.volunteer.service.IGlobalVolunteerService;

@RestController
public class GlobalVolunteerController {
	
	@Autowired
	IGlobalVolunteerService serviceObj;
	
	@PostMapping
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

	

}
