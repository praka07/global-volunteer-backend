package com.global.volunteer.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.global.volunteer.dao.IGlobalVolunteerServiceDao;
import com.global.volunteer.model.ActivityDetails;
import com.global.volunteer.model.FeedBack;
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
	@Override
	public List<ActivityDetails> getUpcomingActivitiesForVolunteers(int volunteerId) {
		return daoObject.getUpcomingActivitiesForVolunteers(volunteerId);
	}
	@Override
	public ResponseEntity<?> registerActivity(String requestPayload) {
		return daoObject.registerActivity(requestPayload);
	}
	
	@Override
	public ResponseEntity<?> cancelActivity(String requestPayload) {
		return daoObject.cancelActivity(requestPayload);
	}
	@Override
	public List<ActivityDetails> volunteerRegisteredActivities(int volunteerId) {
		return daoObject.volunteerRegisteredActivities(volunteerId);
	}
	@Override
	public ResponseEntity<?> activitycheckIn(String requestPayload) {
		return daoObject.activitycheckIn(requestPayload);
		
	}
	@Override
	public ResponseEntity<?> activitycheckOut(String requestPayload) {
		return daoObject.activitycheckOut(requestPayload);
		
	}
	@Override
	public ResponseEntity<?> getReportForSystemAdministrator() {
		return daoObject.getReportForSystemAdministrator();
	}
	@Override
	public List<ActivityDetails> getHomePageActivityList() {
		return daoObject.getHomePageActivityList();
		
	}
	@Override
	public List<FeedBack> getFeedBackByUserId(int loggedInUserId) {
		return daoObject.getFeedBackByUserId(loggedInUserId);
	}
	@Override
	public ResponseEntity<?> editFeedBackByUserId(FeedBack updateObhject) {
		return daoObject.editFeedBackByUserId(updateObhject);
	}
	@Override
	public List<ActivityDetails> attendedActivityListById(int loggedInUserId) {
		return daoObject.attendedActivityListById(loggedInUserId);
	}
	@Override
	public ResponseEntity<?> createFeedback(String requetPayload) {
		return daoObject.createFeedback(requetPayload);
	}

	@Override
	public ResponseEntity<?> deletedFeedbackByid(int feedbackId) {
		return daoObject.deletedFeedbackByid(feedbackId);
	}


}
