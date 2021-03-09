package com.global.volunteer.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.global.volunteer.dao.IGlobalVolunteerServiceDao;
import com.global.volunteer.model.ActivityDetails;
import com.global.volunteer.model.User;

@Service
public class IGlobalVolunteerService implements GlobalVolunteerService {
	
	@Autowired
	IGlobalVolunteerServiceDao daoObject;
	

	@Override
	public ResponseEntity<?> validateUserLogin(JSONObject loginPayload) {
		return daoObject.validateUserLogin(loginPayload);
	}

	@Override
	public ResponseEntity<?> registerUser(User newUserInfo) {
		return daoObject.registerUser(newUserInfo);
	}
	@Override
	public List<User> getAllUsers() {
		return daoObject.getAllUsers();
	}
	@Override
	public ResponseEntity<?> updateUser(User updateUserInfo) {
		return daoObject.updateUser(updateUserInfo);
	}
	@Override
	public ResponseEntity<?> updatePasswordById(String information) {
		return daoObject.updatePasswordById(information);
	}
	@Override
	public ResponseEntity<?> createActivity(ActivityDetails activityDetails) {
		return daoObject.createActivity(activityDetails);
	}
	@Override
	public List<ActivityDetails> getAllActivities() {
		return daoObject.getAllActivities();
	}
	@Override
	public ResponseEntity<?> updateActivity(ActivityDetails activityDetails) {
		return daoObject.updateActivity(activityDetails);
	}

}
