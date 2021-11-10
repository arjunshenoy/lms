CREATE TABLE IF NOT EXISTS `Department` (
    `department_id` int NOT NULL AUTO_INCREMENT,
    `department_name` varchar(255)  NOT NULL,
    `head_id` varchar(255),
	PRIMARY KEY (`department_id`)
);


CREATE TABLE IF NOT EXISTS `leave_history` (
	`leave_request_id` INT NOT NULL,
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
    `reason` VARCHAR(20) NOT NULL,
    `comments` VARCHAR(20) NOT NULL,
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

INSERT INTO `leave_rules`
VALUES (5,'Annual Leave', '2021-12-31',10,2,0,'Sick Leave', 12,'');


