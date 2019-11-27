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