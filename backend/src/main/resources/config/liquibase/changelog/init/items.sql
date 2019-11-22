CREATE TABLE IF NOT EXISTS `easyfood`.`items`
(
    `id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(100) NOT NULL UNIQUE,
    `price`   FLOAT        NOT NULL,
    `type`    BOOL         NOT NULL,
    `kcal`    INT,
    `protein` INT,
    `fat`     INT,
    `carbs`   INT,
    CONSTRAINT `item_Pk` PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`allergies`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT `allergies_PK` PRIMARY KEY (`id`)
);

CREATE TABLE IF NOt EXISTS `easyfood`.`items_allergies`
(
    `item_id`    BIGINT NOT NULL,
    `allergy_id` BIGINT NOT NULL,
    CONSTRAINT `items_allergies_PK` PRIMARY KEY (`item_id`, `allergy_id`),
    CONSTRAINT `items_allergies_FK_item_id` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`),
    CONSTRAINT `items_allergies_FK_allergy_id` FOREIGN KEY (`allergy_id`) REFERENCES `items` (`id`)
);