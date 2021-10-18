CREATE TABLE IF NOT EXISTS `login` (
    `employee_id` INT NOT NULL,
    `email` VARCHAR(30) NOT NULL,
    `created_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`employee_id`)
);