package com.global.volunteer.dao;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public interface GlobalVolunteerServiceDao {


	ResponseEntity<?> validateUserLogin(JSONObject loginPayload);

}
