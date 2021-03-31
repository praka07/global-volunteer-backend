package com.global.volunteer.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.global.volunteer.model.ActivityDetails;
import com.global.volunteer.model.ActivityTransactionDetails;
import com.global.volunteer.model.FeedBack;
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
				+ "place,duration,content,totalNumberOfPeople,createdBy,createdDate,ApprovedBy,approvedDate,conductedBy) values (:activityname,:activityDate,:startTime,:endTime,:place,:duration"
				+ ",:content,:totalNumberOfPeople,:createdBy,:createdDate,:ApprovedBy,:approvedDate,:conductedBy)";
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
		parameters.put("conductedBy", activityDetails.getConductedBy());
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

		String selectQuery = "SELECT * FROM ACTIVITYDETAILS where PARSEDATETIME(replace (activitydate,'/',' '),'dd MMM yyyy','en') >= CURRENT_DATE  ORDER BY ACTIVITYDATE DESC";
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ActivityDetails> getUpcomingActivitiesForVolunteers(int volunteerId) {

		String selectQuery = "SELECT * FROM ACTIVITYDETAILS a where "
				+ "PARSEDATETIME(replace (activitydate,'/',' '),'dd MMM yyyy','en') >= CURRENT_DATE  "
				+ "AND STATUS='A' AND NOT" + " EXISTS(Select '' from activityTransaction where volunteerid="
				+ volunteerId + " and activityid =a.activityid" + ") ORDER BY ACTIVITYDATE DESC";
		log.info("selectQuery -- > {}", selectQuery);
		List<ActivityDetails> list = new ArrayList<ActivityDetails>();
		try {
			list = jdbcTemplate.query(selectQuery, new BeanPropertyRowMapper(ActivityDetails.class));
			return list;
		} catch (Exception e) {
			log.info("getUpcomingActivitiesForVolunteers issue {} ", e);
			return list;

		}

	}

	@Override
	public ResponseEntity<?> updateActivity(ActivityDetails activityDetails) {
		String updateQuery = "update ACTIVITYDETAILS set status =?,ApprovedBy=?,approvedDate=? where activityid=?";
		try {
			jdbcTemplate.update(updateQuery, activityDetails.getStatus(), activityDetails.getApprovedBy(),
					activityDetails.getActivityDate(), activityDetails.getActivityId());
			return ResponseEntity.ok().body("{ \"message\" : \"status updated successfully\"}");
		} catch (Exception e) {
			log.info("update password issue {} ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> registerActivity(String requestPayload) {
		JSONObject request = new JSONObject(requestPayload);
		String activityTransactionInsertion = "insert into ACTIVITYTRANSACTION(activityId,volunteerid,VOLUNTEERAPPLIEDDATE) values"
				+ " (:activityId,:volunteerid,:volunteerapplieddate) ";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("activityId", request.getInt("activityId"));
		parameters.put("volunteerid", request.getInt("volunteerid"));
		parameters.put("volunteerapplieddate", request.getString("volunteerAppliedDate"));

		try {
			int status = namedParameterJdbcTemplate.update(activityTransactionInsertion, parameters);
			if (status == 1) {
				String updateQuery = "update activityDetails set appliedVolunteerCount= appliedVolunteerCount+1 where activityId="
						+ request.getInt("activityId");
				jdbcTemplate.update(updateQuery);
				return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"registered successfully\"}");

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("error while insert activity transaction");
			}

		} catch (Exception e) {

			log.info("error wile registerActivity", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}
	}

	@Override
	public ResponseEntity<?> cancelActivity(String requestPayload) {
		JSONObject request = new JSONObject(requestPayload);
		String updateActivityTransaction = "update activityTransaction set cancel=1, canceledDate='"
				+ request.getString("volunteerCancelDate") + "'" + " where volunteerId=" + request.getInt("volunteerid")
				+ " and  activityId=" + request.getInt("activityId");
		try {
			int status = jdbcTemplate.update(updateActivityTransaction);
			if (status == 1) {
				String updateQuery = "update activityDetails set appliedVolunteerCount= appliedVolunteerCount-1 where activityId="
						+ request.getInt("activityId");
				jdbcTemplate.update(updateQuery);

				return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"cancelled successfully\"}");

			} else {

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("error while cancel activity transaction");

			}
		} catch (Exception e) {

			log.info("error wile cancelActivity", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ActivityDetails> volunteerRegisteredActivities(int volunteerId) {
		String selectQuery = "SELECT * FROM GLOBALVOLUNTEER.ACTIVITYDETAILS  as a inner join "
				+ "GLOBALVOLUNTEER.ACTIVITYTRANSACTION  as "
				+ "b on a.activityid = b.activityid where isnull(b.cancel,0)=0 and  b.volunteerid=" + volunteerId;
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
	public ResponseEntity<?> activitycheckIn(String requestPayload) {

		JSONObject request = new JSONObject(requestPayload);
		String checkEitherCheckedInOrNotQuery = "select * from activityTransaction where checkindate is null and volunteerId="
				+ request.getInt("volunteerid") + " and  activityId=" + request.getInt("activityId");

		if (checkInCheckOutValidate(checkEitherCheckedInOrNotQuery)) {
			String updateActivityTransaction = "update activityTransaction set  attendend=TRUE, checkindate='"
					+ request.getString("checkInDate") + "'" + " where volunteerId=" + request.getInt("volunteerid")
					+ " and  activityId=" + request.getInt("activityId");
			try {
				jdbcTemplate.update(updateActivityTransaction);
				return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\" activitychecked In successfully\"}");

			} catch (Exception e) {

				log.info("error wile check in", e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

			}

		} else {
			return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"checkedin already\"}");

		}

	}

	@Override
	public ResponseEntity<?> activitycheckOut(String requestPayload) {

		JSONObject request = new JSONObject(requestPayload);
		String updateActivityTransaction = "update activityTransaction set  checkoutdate='"
				+ request.getString("checkoutdate") + "'" + " where volunteerId=" + request.getInt("volunteerid")
				+ " and  activityId=" + request.getInt("activityId");
		String checkEitherCheckedOutOrNotQuery = "select * from activityTransaction where checkoutdate is null and volunteerId="
				+ request.getInt("volunteerid") + " and  activityId=" + request.getInt("activityId");

		if (checkInCheckOutValidate(checkEitherCheckedOutOrNotQuery)) {
			try {
				int status = jdbcTemplate.update(updateActivityTransaction);
				return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"activitychecked In successfully\"}");

			} catch (Exception e) {

				log.info("error wile check in", e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

			}

		} else {

			return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"checkedout already\"}");
		}

	}

	@SuppressWarnings("rawtypes")
	public boolean checkInCheckOutValidate(String query) {
		log.info("Query -- {} ", query);
		try {
			@SuppressWarnings("unchecked")
			List<ActivityTransactionDetails> status = jdbcTemplate.query(query,
					new BeanPropertyRowMapper(ActivityTransactionDetails.class));
			if (status.isEmpty()) {
				return false;

			} else {
				return true;
			}

		} catch (Exception e) {
			log.info("error wile checkInCheckOutValidate", e);
			return false;
		}
	}

	@Override
	public ResponseEntity<?> getReportForSystemAdministrator() {
		String query = "select activityName, (select count(*) from GLOBALVOLUNTEER.ACTIVITYTRANSACTION as b where b.ACTIVITYID=a.ACTIVITYID and isnull(b.CANCEL,0) = 0) as volunteerCount "
				+ "from GLOBALVOLUNTEER.ACTIVITYDETAILS as a "
				+ "where datediff(d,PARSEDATETIME(replace (activitydate,'/',' '),'dd MMM yyyy','en'),now()) <=30 and datediff(d,PARSEDATETIME(replace (activitydate,'/',' '),'dd MMM yyyy','en'),now()) >=0";
		JSONArray responseArray = new JSONArray();
		try {
			List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
			for (@SuppressWarnings("rawtypes")
			Map m : result) {
				JSONObject responseObject = new JSONObject();
				responseObject.put("activityName", m.get("activityName").toString());
				responseObject.put("volunteerCount", m.get("volunteerCount").toString());
				responseArray.put(responseObject);
			}

			return ResponseEntity.status(HttpStatus.OK).body(responseArray.toString());

		} catch (DataAccessException e) {
			log.info("getReportForSystemAdministrator error {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ActivityDetails> getHomePageActivityList() {
		String selectQuery = "SELECT TOP 4* FROM ACTIVITYDETAILS where "
				+ "PARSEDATETIME(replace (activitydate,'/',' '),'dd MMM yyyy','en') >= CURRENT_DATE and status = 'A' "
				+ "ORDER BY ACTIVITYDATE DESC";
		log.info("selectQuery in getHomePageActivityList method-- > {}", selectQuery);
		List<ActivityDetails> list = new ArrayList<ActivityDetails>();
		try {
			list = jdbcTemplate.query(selectQuery, new BeanPropertyRowMapper(ActivityDetails.class));
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<FeedBack> getFeedBackByUserId(int loggedInUserId) {
		String getFeedBackQuery = "select a.id,comments,createdDate,attachmentName,attachmentContent from feedback as a inner join "
				+ "feedbackAttachment as b on a.id =b.feedbackId where a.createdBy=" + loggedInUserId;
		log.info("getFeedBackQuery ::: {}", getFeedBackQuery);
		List<FeedBack> feedBackResult = new ArrayList<FeedBack>();
		try {
			feedBackResult = jdbcTemplate.query(getFeedBackQuery, new BeanPropertyRowMapper(FeedBack.class));

			List<FeedBack> retuenFeedBackResult = new ArrayList<FeedBack>();

			if (feedBackResult.size() > 1) {
				FeedBack obj = new FeedBack();
				obj.setId(feedBackResult.get(0).getId());
				obj.setComments(feedBackResult.get(0).getComments());
				obj.setCreatedDate(feedBackResult.get(0).getCreatedDate());
				List<String> attachmentName = new ArrayList<String>();
				List<String> attachmentContents = new ArrayList<String>();

				for (FeedBack feedback : feedBackResult) {
					attachmentName.add(feedback.getAttachmentName());
					attachmentContents.add(feedback.getAttachmentContent());
				}

				obj.setAttachmentName(attachmentName.toString().replace("[", "").replace("]", ""));
				obj.setAttachmentContent(attachmentContents.toString().replace("[", "").replace("]", ""));
				retuenFeedBackResult.add(obj);
				return retuenFeedBackResult;

			} else {
				return feedBackResult;
			}

		} catch (Exception e) {
			log.info("error during getFeedBackByUserId {}", e);
			return feedBackResult;
		}
	}

	@Override
	public ResponseEntity<?> editFeedBackByUserId(FeedBack updateObhject) {
		try {
			String updateQuery = "update feedback set comments =? where id=?";
			jdbcTemplate.update(updateQuery, updateObhject.getComments(), updateObhject.getId());

			return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"updated successfully\"}");

		} catch (JSONException e) {
			log.info("createFeedback error {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ActivityDetails> attendedActivityListById(int loggedInUserId) {
		String selectQuery = "select  b.* from activityTransaction as a inner join activityDetails as b on "
				+ "a.activityId=b.activityId where attendend =TRUE and volunteerId=" + loggedInUserId
				+ " and  not exists ( select '' from feedback where activityid = a.activityId)";
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
	public ResponseEntity<?> createFeedback(String requetPayload) {
		try {
			JSONObject requestObject = new JSONObject(requetPayload);
			String insertFeedBackQuery = "insert into feedback (activityId,comments,createdBy,createdDate) values"
					+ "(:activityId,:comments,:createdBy,:createdDate) ";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("activityId", requestObject.get("activityId"));
			parameters.put("comments", requestObject.get("comments"));
			parameters.put("createdBy", requestObject.get("createdBy"));
			parameters.put("createdDate", requestObject.get("createdDate"));
			namedParameterJdbcTemplate.update(insertFeedBackQuery, parameters);
			log.info("Feed back inserted");
			String getFeedbackId = "select * from feedback where activityId=" + requestObject.get("activityId");
			List<Map<String, Object>> result = jdbcTemplate.queryForList(getFeedbackId);
			int feedbackId = 0;
			for (@SuppressWarnings("rawtypes")
			Map m : result) {
				feedbackId = (int) m.get("id");
			}
			JSONArray attachmentArray = requestObject.getJSONArray("attachmentContent");

			for (int i = 0; i < attachmentArray.length(); i++) {
				JSONObject attachmentObject = attachmentArray.getJSONObject(i);
				String query = "insert into feedbackAttachment (feedbackId,attachmentName,attachmentContent) values ("
						+ feedbackId + ",'" + attachmentObject.get("name") + "','" + attachmentObject.get("content")
						+ "')";
				log.info(" insert feed back image query >-- > {}", query);

				jdbcTemplate.update(query);

			}

			return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"feedback created  successfully \"}");
		} catch (DataAccessException e) {
			log.info("createFeedback error {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} catch (JSONException e) {
			log.info("createFeedback error {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> deletedFeedbackByid(int feedbackId) {
		try {
			String deleteFeedbackAttachment = "delete from  feedbackAttachment where feedbackId=" + feedbackId;
			jdbcTemplate.update(deleteFeedbackAttachment);
			String deleteFeedback = "delete from  feedback where id=" + feedbackId;
			jdbcTemplate.update(deleteFeedback);

			return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"feedback deleted  successfully \"}");
		} catch (Exception e) {
			log.info("deletedFeedbackByid error {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
