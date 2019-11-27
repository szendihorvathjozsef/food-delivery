CREATE TABLE IF NOT EXISTS `easyfood`.`coupon`
(
    `id`        BIGINT      NOT NULL AUTO_INCREMENT,
    `percent`   INT         NOT NULL,
    `type_name` VARCHAR(50) NOT NULL,
    CONSTRAINT `coupon_PK` PRIMARY KEY (`id`),
    CONSTRAINT `coupon_FK_item_type_name` FOREIGN KEY (`type_name`) REFERENCES `easyfood`.`item_type` (`name`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`menu`
(
    `id`          BIGINT                              NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(50)                         NOT NULL,
    `description` VARCHAR(250),
    `created_on`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    `updated_on`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    `start_time`  DATE                                NULL,
    `end_time`    DATE                                NULL,
    CONSTRAINT `coupon_PK` PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`menu_item`
(
    `menu_id` BIGINT NOT NULL,
    `item_id` BIGINT NOT NULL,
    CONSTRAINT `menu_item_PK` PRIMARY KEY (`menu_id`, `item_id`),
    CONSTRAINT `menu_item_FK_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `easyfood`.`menu` (`id`),
    CONSTRAINT `menu_item_FK_item_item` FOREIGN KEY (`item_id`) REFERENCES `easyfood`.`item` (`id`)
)