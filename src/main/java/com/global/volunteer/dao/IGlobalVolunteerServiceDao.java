package com.global.volunteer.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.global.volunteer.model.ActivityDetails;
import com.global.volunteer.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class IGlobalVolunteerServiceDao implements GlobalVolunteerServiceDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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

	@Override
	public ResponseEntity<?> registerUser(User newUserInfo) {
		String checkUserPresent = "select count(*) from users where emailId ='" + newUserInfo.getEmailId() + "'";
		log.info("query to checkCustomerPresent  {} ", checkUserPresent);
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			int status = jdbcTemplate.queryForObject(checkUserPresent, Integer.class);
			if (status > 0) {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
						.body("{ \"message\" : \"emailId already registered with another user\"}");
			} else {
				String queryToExecute = "insert into users(firstName,lastName,emailId,phoneNumber,"
						+ "password,createdDate,createdBy,role) values (:firstName,:lastName,:emailId,:phoneNumber,:password"
						+ ",:createdDate,:createdBy,:role)";

				parameters.put("firstName", newUserInfo.getFirstName());
				parameters.put("lastName", newUserInfo.getLastName());
				parameters.put("emailId", newUserInfo.getEmailId());
				parameters.put("phoneNumber", newUserInfo.getPhoneNumber());
				parameters.put("password", "abc");
				parameters.put("createdDate", newUserInfo.getCreatedDate());
				parameters.put("createdBy", newUserInfo.getCreatedBy());
				parameters.put("role", newUserInfo.getRole());
				namedParameterJdbcTemplate.update(queryToExecute, parameters);

				return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"user registered successfully\"}");

			}
		} catch (Exception e) {

			log.info("error wile create user", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> getAllUsers() {

		String selectQuery = "Select * from users where role <> 1";
		log.info("selectQuery -- > {}", selectQuery);
		List<User> users = new ArrayList<User>();
		try {
			users = jdbcTemplate.query(selectQuery, new BeanPropertyRowMapper(User.class));
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return users;

		}

	}

	@Override
	public ResponseEntity<?> updateUser(User updateUserInfo) {
		log.info("Update User request {}", updateUserInfo);
		try {
			int status = jdbcTemplate.update(
					"update users set firstName =?,lastName=?,emailId =?,phoneNumber=?,active=?,role=? where userId =?",
					updateUserInfo.getFirstName(), updateUserInfo.getLastName(), updateUserInfo.getEmailId(),
					updateUserInfo.getPhoneNumber(), updateUserInfo.isActive(), updateUserInfo.getRole(),
					updateUserInfo.getUserId());
			log.info("updated status {}", status);
			return ResponseEntity.status(HttpStatus.OK).body("{ \"message\" : \"updated successfully\"}");

		} catch (Exception e) {
			log.info("update issue {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}
	}

	@Override
	public ResponseEntity<?> updatePasswordById(String information) {
		JSONObject updateInfo = new JSONObject(information);
		String updateQuery = "update users set password =? where userid=?";
		try {
			jdbcTemplate.update(updateQuery, updateInfo.getString("newPass"), updateInfo.getInt("userId"));
			return ResponseEntity.ok().body("{ \"message\" : \"updated successfully\"}");
		} catch (Exception e) {
			log.info("update password issue {} ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> createActivity(ActivityDetails activityDetails) {

		String queryToExecute = "insert into activityDetails(activityName,activityDate,startTime,endTime,"
				+ "place,duration,content,totalNumberOfPeople,createdBy,createdDate,ApprovedBy,approvedDate) values (:activityname,:activityDate,:startTime,:endTime,:place,:duration"
				+ ",:content,:totalNumberOfPeople,:createdBy,:createdDate,:ApprovedBy,:approvedDate)";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("activityname", activityDetails.getActivityName());
		parameters.put("activityDate", activityDetails.getActivityDate());
		parameters.put("startTime", activityDetails.getStartTime());
		parameters.put("endTime", activityDetails.getEndTime());
		parameters.put("place", activityDetails.getPlace());
		parameters.put("duration", activityDetails.getDuration());
		parameters.put("content", activityDetails.getContent());
		parameters.put("totalNumberOfPeople", activityDetails.getTotalNumberOfPeople());
		parameters.put("createdBy", activityDetails.getCreatedBy());
		parameters.put("createdDate", activityDetails.getCreatedDate());
		parameters.put("ApprovedBy", activityDetails.getApprovedBy());
		parameters.put("approvedDate", activityDetails.getApprovedDate());
		try {
			namedParameterJdbcTemplate.update(queryToExecute, parameters);
			return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"user registered successfully\"}");

		} catch (Exception e) {

			log.info("error wile createActivity", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ActivityDetails> getAllActivities() {
		
		String selectQuery = "SELECT * FROM ACTIVITYDETAILS ORDER BY ACTIVITYDATE DESC";
		log.info("selectQuery -- > {}", selectQuery);
		List<ActivityDetails> list = new ArrayList<ActivityDetails>();
		try {
			list = jdbcTemplate.query(selectQuery, new BeanPropertyRowMapper(ActivityDetails.class));
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;

		}
		
	}
	@Override
	public ResponseEntity<?> updateActivity(ActivityDetails activityDetails) {
		String updateQuery = "update ACTIVITYDETAILS set status =? where activityid=?";
		try {
			jdbcTemplate.update(updateQuery, activityDetails.isStatus(), activityDetails.getActivityId());
			return ResponseEntity.ok().body("{ \"message\" : \"status updated successfully\"}");
		} catch (Exception e) {
			log.info("update password issue {} ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
