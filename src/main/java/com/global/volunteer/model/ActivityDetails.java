/**
 * 
 */
package com.global.volunteer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Administrator
 *
 */
@Getter
@Setter
@ToString
public class ActivityDetails {
	private int activityId;
	private String activityName;
	private String activityDate;
	private String startTime;
	private String endTime;
	private String place;
	private int duration;
	private String content;
	private int totalNumberOfPeople;
	private int createdBy;
	private String createdDate;
	private int ApprovedBy;
	private boolean status;
	private String approvedDate;
	

}
