ALTER TABLE `easyfood`.`order_item`
    MODIFY COLUMN `order_id` BIGINT NULL;
ALTER TABLE `easyfood`.`order_item`
    MODIFY COLUMN `item_id` BIGINT NULL;

ALTER TABLE `easyfood`.`food_order`
    MODIFY COLUMN `user_id` BIGINT NULL;

ALTER TABLE `easyfood`.`item`
    MODIFY COLUMN `item_type_name` VARCHAR(50) NULL;

ALTER TABLE `easyfood`.`user_address`
    MODIFY COLUMN `user_id` BIGINT NULL;
ALTER TABLE `easyfood`.`coupon`
    MODIFY COLUMN `type_name` VARCHAR(50) NULL;