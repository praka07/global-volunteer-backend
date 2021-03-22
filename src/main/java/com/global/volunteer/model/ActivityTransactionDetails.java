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
public class ActivityTransactionDetails {
	private int id;
	private int activityId;
	private int volunteerId;
	private String volunteerApplieddate;
	private boolean cancel;
	private String canceledDate;
	private String checkInDate;
	private String checkOutDate;
	private boolean attendend;

}
