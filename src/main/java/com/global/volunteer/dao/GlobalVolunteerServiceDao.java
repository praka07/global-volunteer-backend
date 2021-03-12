package com.global.volunteer.dao;

import java.util.List;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.global.volunteer.model.ActivityDetails;
import com.global.volunteer.model.User;

public interface GlobalVolunteerServiceDao {


	ResponseEntity<?> validateUserLogin(JSONObject loginPayload);

	ResponseEntity<?> registerUser(User newUserInfo);

	List<User> getAllUsers();

	ResponseEntity<?> updateUser(User updateUserInfo);

	ResponseEntity<?> updatePasswordById(String information);

	ResponseEntity<?> createActivity(ActivityDetails activityDetails);

	List<ActivityDetails> getAllActivities();

	ResponseEntity<?> updateActivity(ActivityDetails activityDetails);

	List<ActivityDetails> getUpcomingActivitiesForVolunteers(int volunteerId);

	ResponseEntity<?> registerActivity(String requestPayload);

	ResponseEntity<?> cancelActivity(String requestPayload);

	List<ActivityDetails> volunteerRegisteredActivities(int volunteerId);

	ResponseEntity<?> activitycheckIn(String requestPayload);

	ResponseEntity<?> activitycheckOut(String requestPayload);

	ResponseEntity<?> getReportForSystemAdministrator();

}
