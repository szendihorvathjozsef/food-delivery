ALTER TABLE `easyfood`.`coupon` ADD COLUMN `user_id` BIGINT NULL;
ALTER TABLE `easyfood`.`coupon` ADD CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);