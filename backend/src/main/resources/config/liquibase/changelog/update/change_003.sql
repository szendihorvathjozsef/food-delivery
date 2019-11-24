ALTER TABLE `easyfood`.`order` ADD COLUMN `status` VARCHAR(50) NOT NULL;
ALTER TABLE `easyfood`.`order` ADD  COLUMN  `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL;
ALTER TABLE `easyfood`.`order` ADD  COLUMN  `updated_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL;
ALTER TABLE `easyfood`.`order` ADD  COLUMN  `start_time` DATE NULL;
ALTER TABLE `easyfood`.`order` ADD  COLUMN  `end_time` DATE NULL;