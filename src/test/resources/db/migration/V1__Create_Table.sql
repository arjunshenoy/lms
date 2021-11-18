CREATE TABLE IF NOT EXISTS `Department` (
    `department_id` int NOT NULL,
    `department_name` varchar(255)  NOT NULL,
    `head_id` varchar(255),
	PRIMARY KEY (`department_id`)
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
VALUES (1,'Casual Leave', '2021-12-01',5.5, 10,10,'ABC', 20, 'abc')


