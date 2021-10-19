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

@Entity
@Table(name = "Department")
@Getter
@Setter
public class Department {
    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;
    
    @Column(name = "department_name")
    private String departmentName;
    
    @Column(name = "head_id")
    private String headId;
}
