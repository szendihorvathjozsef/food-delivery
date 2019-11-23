CREATE TABLE IF NOT EXISTS `easyfood`.`item_type`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT `item_type_PK` PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`item`
(
    `id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(100) NOT NULL UNIQUE,
    `price`   FLOAT        NOT NULL,
    `item_type_id` BIGINT       NOT NULL,
    `kcal`    INT,
    `protein` INT,
    `fat`     INT,
    `carbs`   INT,
    CONSTRAINT `items_Pk` PRIMARY KEY (`id`),
    CONSTRAINT `items_FK_item_type_id` FOREIGN KEY (`item_type_id`) REFERENCES `item_type`(`id`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`allergen`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT `allergen_PK` PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`items_allergen`
(
    `item_id`    BIGINT NOT NULL,
    `allergen_id` BIGINT NOT NULL,
    CONSTRAINT `items_allergies_PK` PRIMARY KEY (`item_id`, `allergen_id`),
    CONSTRAINT `items_allergies_FK_item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
    CONSTRAINT `items_allergies_FK_allergen_id` FOREIGN KEY (`allergen_id`) REFERENCES `allergen` (`id`)
);