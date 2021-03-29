/**
 * 
 */
package com.global.volunteer.model;

import java.util.List;

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
public class FeedBack {
	private int id;
	private int transactionId;
	private String comments;
	private String attachmentName;
	private int createdBy;
	private String createdDate;
	private String attachmentContent;
	

}
