package com.germanium.lms.models;

// @author: Chinmay Jose K M

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "User")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "dob")
    private Date dob;
    
    @Column(name = "date_of_joining")
    private Date dateOfJoining;
    
    @Column(name = "is_permanent")
    private boolean isPermanent;
    
    @Column(name = "department_id")
    private int departmentId;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "created_timestamp")
    private Timestamp createdTs;
    
    @Column(name = "updated_timestamp")
    private Timestamp updatedTs;
    
    @Column(name = "is_active")
	private boolean isActive;
}