CREATE TABLE IF NOT EXISTS `easyfood`.`user`
(
    `id`             BIGINT       NOT NULL AUTO_INCREMENT,
    `login`          VARCHAR(100) NOT NULL,
    `password_hash`  VARCHAR(60)  NOT NULL,
    `first_name`     VARCHAR(50),
    `last_name`      VARCHAR(50),
    `email`          VARCHAR(254),
    `status`         VARCHAR(25)  NOT NULL,
    `activation_key` varchar(20)  NULL,
    `reset_key`      varchar(20)  NULL,
    CONSTRAINT `users_PK` PRIMARY KEY (`id`),
    CONSTRAINT `users_UN` UNIQUE KEY (`login`, `email`)
);

INSERT INTO `easyfood`.`user`
(id, login, password_hash, first_name, last_name, email, status, activation_key, reset_key)
VALUES (1, 'admin', '$2y$12$ld.Xx04Z/rrqxN389kx.qeQ62j8WVmWgDUuRCjpddc6zzaNXCfcwK', 'Joe', 'Administrator',
        'admin@easy-food.local', 'ACTIVE', NULL, NULL),
       (2, 'user', '$2y$12$ld.Xx04Z/rrqxN389kx.qeQ62j8WVmWgDUuRCjpddc6zzaNXCfcwK', 'Greg', 'User',
        'user@easy-food.local', 'ACTIVE', NULL, NULL);

CREATE TABLE IF NOT EXISTS `easyfood`.`user_address`
(
    `id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`   BIGINT       NOT NULL,
    `address`   VARCHAR(100) NOT NULL,
    `post_code` INT          NOT NULL,
    `type`      VARCHAR(25)  NOT NULL,
    CONSTRAINT `address_PK` PRIMARY KEY (`id`),
    CONSTRAINT `address_FK` FOREIGN KEY (`user_id`) REFERENCES `easyfood`.`user`(`id`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`authority`
(
    `name` VARCHAR(50) NOT NULL,
    CONSTRAINT authorities_PK PRIMARY KEY (`name`)
);

INSERT INTO `easyfood`.`authority` (`name`)
VALUES ('ADMINISTRATOR'),
       ('USER');

CREATE TABLE IF NOT EXISTS `easyfood`.`user_authority`
(
    `user_id`        BIGINT      NOT NULL,
    `authority_name` VARCHAR(50) NOT NULL,
    CONSTRAINT `user_authorities_PK` PRIMARY KEY (`user_id`, `authority_name`),
    CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `easyfood`.`user`(`id`),
    CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `easyfood`.`authority`(`name`)
);

INSERT INTO `easyfood`.`user_authority` (user_id, authority_name)
VALUES (1, 'ADMINISTRATOR'),
       (2, 'USER');

CREATE TABLE IF NOT EXISTS `easyfood`.`item_type`
(
    `name` VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT `item_type_PK` PRIMARY KEY (`name`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`item`
(
    `id`             BIGINT       NOT NULL AUTO_INCREMENT,
    `name`           VARCHAR(100) NOT NULL UNIQUE,
    `price`          FLOAT        NOT NULL,
    `item_type_name` VARCHAR(50)  NOT NULL,
    `kcal`           INT,
    `protein`        INT,
    `fat`            INT,
    `carbs`          INT,
    CONSTRAINT `items_Pk` PRIMARY KEY (`id`),
    CONSTRAINT `items_FK_item_type_name` FOREIGN KEY (`item_type_name`) REFERENCES `easyfood`.`item_type`(`name`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`allergen`
(
    `name` VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT `allergen_PK` PRIMARY KEY (`name`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`items_allergen`
(
    `item_id`       BIGINT NOT NULL,
    `allergen_name` VARCHAR(50) NOT NULL,
    CONSTRAINT `items_allergies_PK` PRIMARY KEY (`item_id`, `allergen_name`),
    CONSTRAINT `items_allergies_FK_item_id` FOREIGN KEY (`item_id`) REFERENCES `easyfood`.`item` (`id`),
    CONSTRAINT `items_allergies_FK_allergen_name` FOREIGN KEY (`allergen_name`) REFERENCES `easyfood`.`allergen` (`name`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`order`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `user_id`    BIGINT NOT NULL,
    `total_cost` DOUBLE,
    CONSTRAINT `orders_PK` PRIMARY KEY (`id`),
    CONSTRAINT `orders_FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `easyfood`.`user`(`id`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`order_item`
(
    `id`       BIGINT NOT NULL AUTO_INCREMENT,
    `order_id` BIGINT NOT NULL,
    `item_id`  BIGINT NOT NULL,
    `quantity` INT    NOT NULL,
    CONSTRAINT `order_item_PK` PRIMARY KEY (`id`),
    CONSTRAINT `order_item_FK_order` FOREIGN KEY (`order_id`) REFERENCES `easyfood`.`order` (`id`),
    CONSTRAINT `order_item_FK_item` FOREIGN KEY (`item_id`) REFERENCES `easyfood`.`item` (`id`)
);

ALTER TABLE easyfood.`user` ADD created_by varchar(50) NOT NULL;
ALTER TABLE easyfood.`user` ADD created_date TIMESTAMP NULL;
ALTER TABLE easyfood.`user` ADD last_modified_by varchar(50) NULL;
ALTER TABLE easyfood.`user` ADD last_modified_date TIMESTAMP NULL;

UPDATE `easyfood`.`user`
SET `password_hash` = '$2a$10$BVy7uanHYQaDdbGtPB8oe.nXBFPEAFJFq6lqg4b0Ql3BV5RzzYkwS'
WHERE `id` IN (1, 2);

ALTER TABLE `easyfood`.`order`
    ADD COLUMN `status` VARCHAR(50) NOT NULL;
ALTER TABLE `easyfood`.`order`
    ADD COLUMN `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL;
ALTER TABLE `easyfood`.`order`
    ADD COLUMN `updated_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL;
ALTER TABLE `easyfood`.`order`
    ADD COLUMN `start_time` DATE NULL;
ALTER TABLE `easyfood`.`order`
    ADD COLUMN `end_time` DATE NULL;

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
);

INSERT INTO `easyfood`.`item_type` (`name`)
VALUES ('Leves'), ('Főétel'), ('Tészta'), ('Desszert');

INSERT INTO `easyfood`.`item` (`id`, `name`, `price`, `kcal`, `protein`, `fat`, `carbs`, `item_type_name`)
VALUES (1, 'Milánói spagetti', 1500, 156, 10, 10, 10, 'Tészta'),
       (2, 'Újházi tyúkhúsleves', 800, 100, 12, 12, 12, 'Leves'),
       (3, 'Aranygaluska', 650, 100, 12, 12, 12, 'Desszert');

INSERT INTO `easyfood`.`allergen` (`name`)
VALUES ('tejfehérje'),
       ('tojás'),
       ('mogyoró'),
       ('szója'),
       ('dió'),
       ('kagyló'),
       ('eper');

INSERT INTO `easyfood`.`items_allergen` (item_id, allergen_name)
VALUES (3, 'dió'),
       (3, 'tojás'),
       (3, 'tejfehérje');

ALTER TABLE `easyfood`.`item` ADD COLUMN `image_name` VARCHAR(255);

ALTER TABLE `easyfood`.`coupon` ADD COLUMN `name` VARCHAR(50) NOT NULL;
ALTER TABLE `easyfood`.`order` MODIFY COLUMN `start_time` DATETIME NULL;
ALTER TABLE `easyfood`.`order` MODIFY COLUMN `end_time` DATETIME NULL;

ALTER TABLE `easyfood`.`order` RENAME `food_order`;

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

ALTER TABLE `easyfood`.`coupon` ADD COLUMN `user_id` BIGINT NULL;
ALTER TABLE `easyfood`.`coupon` ADD CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);