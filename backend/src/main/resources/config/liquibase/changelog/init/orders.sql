CREATE TABLE IF NOT EXISTS `easyfood`.`orders`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `user_id`    BIGINT NOT NULL,
    `total_cost` DOUBLE,
    CONSTRAINT `orders_PK` PRIMARY KEY (`id`),
    CONSTRAINT `orders_FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`order_item`
(
    `id`       BIGINT NOT NULL AUTO_INCREMENT,
    `order_id` BIGINT NOT NULL,
    `item_id`  BIGINT NOT NULL,
    `quantity` INT    NOT NULL,
    CONSTRAINT `order_item_PK` PRIMARY KEY (`id`),
    CONSTRAINT `order_item_FK_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
    CONSTRAINT `order_item_FK_item` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`)
);