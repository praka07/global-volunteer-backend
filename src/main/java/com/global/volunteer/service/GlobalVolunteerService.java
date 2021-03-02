package com.global.volunteer.service;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public interface GlobalVolunteerService {


	ResponseEntity<?> validateUserLogin(JSONObject loginPayload);
	

}
