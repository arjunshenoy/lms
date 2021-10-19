package com.germanium.lms.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * @author, Ajin Pius Michel
 */

@Entity
@Table(name = "notification")
@Getter
@Setter
@ApiModel(value = "Notification", description = "Class used to send notification messages")
public class Notification {
	
	@Id
	@Column(name = "notification_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "notificationId")
	private int notificationId;
	
	// Need a change here after model creation. 
	@Column(name = "employee_id")
	// This is employee object..not int
	@ApiModelProperty(value = "employeeId")
	private int employeeId;
	
	@Column(name = "message")
	@ApiModelProperty(value = "message")
	private String message;
	
	@Column(name = "is_read")
	@ApiModelProperty(value = "isRead")
	private boolean isRead;
	
	@Column(name = "title")
	@ApiModelProperty(value = "title")
	private String title;
	
	@Column(name = "date")
	@ApiModelProperty(value = "date")
	private Date date;
}
