ALTER TABLE `easyfood`.`coupon` ADD COLUMN `name` VARCHAR(50) NOT NULL;
ALTER TABLE `easyfood`.`order` MODIFY COLUMN `start_time` DATETIME NULL;
ALTER TABLE `easyfood`.`order` MODIFY COLUMN `end_time` DATETIME NULL;