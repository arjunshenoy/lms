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