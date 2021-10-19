package com.germanium.lms.models;

// @author: Chinmay Jose K M

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "User")
@Getter
@Setter
@ApiModel(value = "User", description = "Class that holds the User details")
public class User {
	
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "employeeId")
    private Integer employeeId;
    
    @Column(name = "first_name")
    @ApiModelProperty(value = "firstName")
    private String firstName;
    
    @Column(name = "middle_name")
    @ApiModelProperty(value = "middleName")
    private String middleName;
    
    @Column(name = "last_name")
    @ApiModelProperty(value = "lastName")
    private String lastName;
    
    @Column(name = "phone_number")
    @ApiModelProperty(value = "phoneNumber")
    private String phoneNumber;
    
    @Column(name = "gender")
    @ApiModelProperty(value = "phoneNumber")
    private String gender;
    
    @Column(name = "dob")
    @ApiModelProperty(value = "dob")
    private Date dob;
    
    @Column(name = "date_of_joining")
    @ApiModelProperty(value = "dateOfJoining")
    private Date dateOfJoining;
    
    @Column(name = "is_permanent")
    @ApiModelProperty(value = "isPermanent")
    private boolean isPermanent;
    
    @Column(name = "department_id")
    @ApiModelProperty(value = "departmentId")
    private int departmentId;
    
    @OneToOne
    @JoinColumn(name="department_id")
    private Department department; 
    
    @Column(name = "role")
    @ApiModelProperty(value = "role")
    private String role;
    
    @Column(name = "address")
    @ApiModelProperty(value = "address")
    private String address;
    
    @Column(name = "created_timestamp")
    @ApiModelProperty(value = "createdTs")
    private Timestamp createdTs;
    
    @Column(name = "updated_timestamp")
    @ApiModelProperty(value = "updatedTs")
    private Timestamp updatedTs;
    
    @Column(name = "is_active")
    @ApiModelProperty(value = "isActive")
	private boolean isActive;
}