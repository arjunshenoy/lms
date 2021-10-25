CREATE TABLE IF NOT EXISTS `login` (
    `employee_id` INT NOT NULL,
    `email` VARCHAR(30) NOT NULL,
    `created_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`employee_id`)
);

CREATE TABLE IF NOT EXISTS `Department` (
    `department_id` int NOT NULL,
    `department_name` varchar(255)  NOT NULL,
    `head_id` varchar(255),
	PRIMARY KEY (`department_id`)
);

CREATE TABLE  IF NOT EXISTS `User` (
    `employee_id` int  NOT NULL,
    `first_name` varchar(255)  NOT NULL,
    `middle_name` varchar(255),
    `last_name` varchar(255)  NOT NULL,
	`phone_number` varchar(15),
	`gender` varchar(15),
	`dob` Date  NOT NULL,
	`date_of_joining` DATE  NOT NULL,
	`is_permanent` BOOLEAN,
	`department_id` int,
	`role` varchar(255),
    `address` varchar(255),
	`created_timestamp` TIMESTAMP,
	`updated_timestamp` TIMESTAMP,
    `is_active` BOOLEAN,
	 PRIMARY KEY (`employee_id`),
     FOREIGN KEY (`department_id`) REFERENCES Department(`department_id`)
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

CREATE TABLE IF NOT EXISTS `leave_history` (
    `employee_id` INT NOT NULL,
    `date_of_application` DATE NOT NULL,
    `leave_id` INT NOT NULL,
    `department_id` INT NOT NULL,
    `from_date` DATE NOT NULL,
    `to_date` DATE NOT NULL,
    `leave_status` VARCHAR(20) NOT NULL,
    `reason` VARCHAR(20) NOT NULL,
    `comments` VARCHAR(20) NOT NULL,
    `decision_date` DATE NOT NULL,
    `updated_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`employee_id`,`date_of_application`)
);

CREATE TABLE IF NOT EXISTS `active_leaves` (
    `employee_id` INT NOT NULL,
    `date_of_application` DATE NOT NULL,
    `leave_id` INT NOT NULL,
    `department_id` INT NOT NULL,
    `from_date` DATE NOT NULL,
    `to_date` DATE NOT NULL,
    `reason` VARCHAR(20) NOT NULL,
    `comments` VARCHAR(20) NOT NULL,
    `updated_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`employee_id`,`date_of_application`)
);

CREATE TABLE IF NOT EXISTS `login` (
    `employee_id` INT NOT NULL,
    `email` VARCHAR(30) NOT NULL,
    `created_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`employee_id`)
);
CREATE TABLE IF NOT EXISTS `leave_rules` (
    `leave_id` INT NOT NULL,
    `name` VARCHAR(150) NOT NULL,
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

CREATE TABLE IF NOT EXISTS `substitution` (
	`substitute_id` INT NOT NULL,
	`leave_id` INT NOT NULL,
	`date_of_approval` DATE NOT NULL,
	PRIMARY KEY (`substitute_id`,`leave_id`)
	FOREIGN KEY (`leave_id`) REFERENCES leave_rules(`leave_id`)
);
