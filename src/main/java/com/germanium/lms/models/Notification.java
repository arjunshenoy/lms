package com.germanium.lms.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * @author, Ajin Pius Michel
 */

@Entity
@Table(name = "notification")
@Getter
@Setter
public class Notification {
	
	@Id
	@Column(name = "notification_id")
	private int notificationId;
	
	// Need a change here after model creation. 
	@Column(name = "employee_id")
	// This is employee object..not int
	private int employeeId;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "is_read")
	private boolean isRead;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "date")
	private Date date;
}
