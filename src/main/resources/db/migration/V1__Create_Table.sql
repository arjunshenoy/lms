CREATE TABLE IF NOT EXISTS `department` (
    `department_id` int NOT NULL AUTO_INCREMENT,
    `department_name` varchar(255)  NOT NULL,
    `head_id` INT (255),
	`created_timestamp` TIMESTAMP,
	`updated_timestamp` TIMESTAMP,
	`created_by` VARCHAR (255),
	`updated_by` VARCHAR (255),
	`working_hours_per_day` FLOAT DEFAULT 0,
	`working_employees_per_day` FLOAT DEFAULT 0,
	`leave_request_queueing` int DEFAULT 0,
	PRIMARY KEY (`department_id`)
);

CREATE TABLE  IF NOT EXISTS `user` (
    `employee_id` int  NOT NULL AUTO_INCREMENT,
    `first_name` varchar(255)  NOT NULL,
    `middle_name` varchar(255),
    `last_name` varchar(255)  NOT NULL,
	`phone_number` varchar(15),
	`email` varchar(255),
	`gender` varchar(15),
	`dob` Date  NOT NULL,
	`date_of_joining` DATE  NOT NULL,
	`is_permanent` BOOLEAN,
	`department_id` int,
	`role` varchar(255),
    `address` varchar(255),
	`created_timestamp` TIMESTAMP,
	`updated_timestamp` TIMESTAMP,
	`created_by` VARCHAR (255),
	`updated_by` VARCHAR (255),
    `is_active` BOOLEAN,
	 PRIMARY KEY (`employee_id`),
     FOREIGN KEY (`department_id`) REFERENCES department(`department_id`)
);


CREATE TABLE IF NOT EXISTS `notification` (
    `notification_id` INT NOT NULL,
    `employee_id`INT NOT NULL,
    `message` VARCHAR(30) NOT NULL,
    `is_read` BOOLEAN DEFAULT FALSE,
    `title` VARCHAR(30) NOT NULL,
    `date` DATE,
    PRIMARY KEY(`notification_id`)
);


CREATE TABLE IF NOT EXISTS `substitution` (
	`substitute_id` INT NOT NULL,
	`leave_id` INT NOT NULL,
	`date_of_approval` DATE NOT NULL,
	PRIMARY KEY (`substitute_id`)
);

CREATE TABLE `login` (
    `employee_id` INT NOT NULL,
    `username` VARCHAR(128) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    `active` BOOLEAN DEFAULT TRUE,
    `roles` VARCHAR(512), 
    `created_ts` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`username`)
);


INSERT INTO login (employee_id,username, password,active, roles, created_ts) VALUES (1,'admin','$2a$10$/A05G9vqMiWmWnFTxa8zDu5IbVhAx19XQ6V4I6ZO6aqClaNpwGFrW',true,'ADMIN', current_timestamp());
INSERT INTO login (employee_id,username, password,active, roles, created_ts) VALUES (2,'arjun','qwerty',true,'STAFF', current_timestamp());
INSERT INTO login (employee_id,username, password,active, roles, created_ts) VALUES (3,'chinmay','qwertyu',true,'ADMIN', current_timestamp());




INSERT INTO `department` (`department_id`,`department_name`,`head_id`,`created_timestamp`,`updated_timestamp`,`created_by`,`updated_by`, `working_hours_per_day`) VALUES(1,'ComputerScience',2,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'admin','admin',40.0); 

INSERT INTO `user` (`employee_id`,`first_name` ,`middle_name` ,`last_name` ,`phone_number`,`email` ,`gender` ,`dob` ,`date_of_joining` ,`is_permanent` ,`department_id` ,`role` ,`address` ,`created_timestamp` ,`updated_timestamp` ,`created_by` ,`updated_by` ,`is_active`) VALUES (1,'ARJUN','KRISHNA','SHENOY','121212121','arjunkshenoy.mec@gmail.com','M','1994-03-09','1994-03-09','1',1,'Staff','asasasasa',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'admin','admin', '1');



INSERT INTO `user` (`employee_id`,`first_name` ,`middle_name` ,`last_name` ,`phone_number` ,`email`,`gender` ,`dob` ,`date_of_joining` ,`is_permanent` ,`department_id` ,`role` ,`address` ,`created_timestamp` ,`updated_timestamp` ,`created_by` ,`updated_by` ,`is_active`) VALUES (2,'CHINMAY','K','JOSE','121212121','aksv1994@gmail.com','M','1994-03-09','1994-03-09','1',1,'HOD','asasasasa',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'admin','admin', '1');

CREATE TABLE IF NOT EXISTS `leave_history` (
	`leave_request_id` INT NOT NULL,
    `employee_id` INT NOT NULL,
    `date_of_application` DATE NOT NULL,
    `leave_id` INT NOT NULL,
    `department_id` INT NOT NULL,
    `from_date` DATE NOT NULL,
    `to_date` DATE NOT NULL,
    `leave_status` VARCHAR(20) NOT NULL,
    `reason` VARCHAR(20),
    `comments` VARCHAR(20),
    `decision_date` DATE NOT NULL,
    `updated_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`leave_request_id`)
);

CREATE TABLE IF NOT EXISTS `active_leaves` (
	`leave_request_id` INT NOT NULL AUTO_INCREMENT,
    `employee_id` INT NOT NULL,
    `date_of_application` DATE NOT NULL,
    `leave_id` INT NOT NULL,
    `department_id` INT NOT NULL,
    `from_date` DATE NOT NULL,
    `to_date` DATE NOT NULL,
    `reason` VARCHAR(20) ,
    `comments` VARCHAR(20) ,
    `updated_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`leave_request_id`)
);

CREATE TABLE IF NOT EXISTS `leave_rules` (
    `leave_id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(150) NOT NULL,
	`lapse_date` DATE,
	`carry_over_count` FLOAT NOT NULL,
	`max_leaves_count` FLOAT NOT NULL,
	`cost_incurred` FLOAT NOT NULL,
	`combinable_leaves` VARCHAR(2000) NOT NULL,
    `number_of_leaves` FLOAT NOT NULL,
    `rule_expression` VARCHAR (2000),     
    PRIMARY KEY(`leave_id`)
);
CREATE TABLE IF NOT EXISTS `leave_stats`(
	`employee_id` INT NOT NULL,
	`leave_id` INT NOT NULL,
	`number_of_leaves` FLOAT NOT NULL,
	PRIMARY KEY (`employee_id`,`leave_id`),
	FOREIGN KEY (`leave_id`) REFERENCES leave_rules(`leave_id`)
);


INSERT INTO `leave_rules`
VALUES (1,'Annual Leave', '2021-12-31',10,2,0,'Casual Leave', 12,'');

INSERT INTO `leave_rules`
VALUES (2,'Casual Leave', '2021-12-31',12,5,0,'Annual Leave', 12,'');
INSERT INTO `leave_rules`
VALUES (3,'Sick Leave', '2021-12-31',0,2,0,'NA', 12,'');

INSERT INTO `leave_rules`
VALUES (4,'Half Pay Leave', '2022-03-31',0,2,0.5,'Casual Leave, Sick Leave', 12,'');


INSERT INTO leave_stats VALUES(1,1,5);
INSERT INTO leave_stats VALUES (1,2,10);





