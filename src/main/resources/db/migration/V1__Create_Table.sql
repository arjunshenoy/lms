CREATE TABLE IF NOT EXISTS `login` (
    `employee_id` INT NOT NULL,
    `email` VARCHAR(30) NOT NULL,
    `created_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`employee_id`)
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

