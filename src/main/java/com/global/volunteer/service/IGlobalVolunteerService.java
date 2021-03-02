package com.global.volunteer.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.global.volunteer.dao.IGlobalVolunteerServiceDao;

@Service
public class IGlobalVolunteerService implements GlobalVolunteerService {
	
	@Autowired
	IGlobalVolunteerServiceDao daoObject;
	

	@Override
	public ResponseEntity<?> validateUserLogin(JSONObject loginPayload) {
		return daoObject.validateUserLogin(loginPayload);
	}

}
