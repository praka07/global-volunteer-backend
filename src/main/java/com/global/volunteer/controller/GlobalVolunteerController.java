package com.global.volunteer.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.global.volunteer.model.ActivityDetails;
import com.global.volunteer.model.FeedBack;
import com.global.volunteer.model.User;
import com.global.volunteer.service.IGlobalVolunteerService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GlobalVolunteerController {

	@Autowired
	IGlobalVolunteerService serviceObj;

	@PostMapping("/validateuserlogin")
	public ResponseEntity<?> validateUserLogin(@RequestBody String requestPayload) {
		JSONObject loginPayload = new JSONObject(requestPayload);
		if ("".equalsIgnoreCase(loginPayload.getString("username")) || null == loginPayload.getString("username")) {
			return ResponseEntity.badRequest().body("{ \"message\" : \"username cannot be empty\"}");

		} else if ("".equalsIgnoreCase(loginPayload.getString("password"))
				|| null == loginPayload.getString("password")) {
			return ResponseEntity.badRequest().body("{ \"message\" : \"password cannot be empty\"}");

		} else {
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

	@PostMapping("/createactivity")
	public ResponseEntity<?> createActivity(@RequestBody ActivityDetails activityDetails) {
		return serviceObj.createActivity(activityDetails);
	}

	@GetMapping("/listactivities")
	public List<ActivityDetails> getAllActivities() {
		return serviceObj.getAllActivities();

	}

	@PutMapping("/updateactivitystatus")
	public ResponseEntity<?> updateActivity(@RequestBody ActivityDetails activityDetails) {
		return serviceObj.updateActivity(activityDetails);
	}

	@GetMapping("/volunteeractivities/{volunteerId}")
	public List<ActivityDetails> getUpcomingActivitiesForVolunteers(@PathVariable int volunteerId) {
		return serviceObj.getUpcomingActivitiesForVolunteers(volunteerId);

	}

	@PostMapping("/registeractivity")
	public ResponseEntity<?> registerActivity(@RequestBody String requestPayload) {
		return serviceObj.registerActivity(requestPayload);
	}

	@PostMapping("/cancelactivity")
	public ResponseEntity<?> cancelActivity(@RequestBody String requestPayload) {
		return serviceObj.cancelActivity(requestPayload);
	}

	@GetMapping("/volunteerregisteractivities/{volunteerId}")
	public List<ActivityDetails> volunteerRegisteredActivities(@PathVariable int volunteerId) {
		return serviceObj.volunteerRegisteredActivities(volunteerId);

	}

	@PostMapping("/activitycheckin")
	public ResponseEntity<?> activitycheckIn(@RequestBody String requestPayload) {
		return serviceObj.activitycheckIn(requestPayload);
	}

	@PostMapping("/activitycheckout")
	public ResponseEntity<?> activitycheckOut(@RequestBody String requestPayload) {
		return serviceObj.activitycheckOut(requestPayload);
	}

	@GetMapping("/report")
	public ResponseEntity<?> getReportForSystemAdministrator() {

		return serviceObj.getReportForSystemAdministrator();

	}

	@GetMapping("/homepageactivitylist")
	public List<ActivityDetails> getHomePageActivityList() {
		return serviceObj.getHomePageActivityList();

	}

	@GetMapping("/getfeedbackinformationbyid/{userid}")
	public List<FeedBack> getFeedBackByUserId(@PathVariable("userid") int loggedInUserId) {
		return serviceObj.getFeedBackByUserId(loggedInUserId);

	}
	@GetMapping("/editfeedbackbyid/{userid}")
	public List<FeedBack> editFeedBackByUserId(@PathVariable("userid") int loggedInUserId) {
		return serviceObj.editFeedBackByUserId(loggedInUserId);

	}
	
	@GetMapping("/checkedinactivitylist/{userid}")	
	public List<ActivityDetails> attendedActivityListById(@PathVariable("userid") int loggedInUserId) {
		return serviceObj.attendedActivityListById(loggedInUserId);

	}
	
	@PostMapping("/createfeedback")
	public ResponseEntity<?> createFeedback(@RequestBody String requetPayload){
		return serviceObj.createFeedback(requetPayload);
	}

	@DeleteMapping("/feedback/{id}")
	public ResponseEntity<?> deletedFeedbackByid(@PathVariable int id){
		return serviceObj.deletedFeedbackByid(id);
		
	}
} 
