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