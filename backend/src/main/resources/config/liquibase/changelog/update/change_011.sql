ALTER TABLE `easyfood`.`user`
    ADD COLUMN `phone_number` VARCHAR(15) NULL;

DROP TABLE `easyfood`.`coupon`;

CREATE TABLE IF NOT EXISTS `easyfood`.`coupon_type`
(
    `id`      BIGINT      NOT NULL AUTO_INCREMENT,
    `percent` INT         NOT NULL,
    `name`    VARCHAR(50) NOT NULL,
    CONSTRAINT `coupon_PK` PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `easyfood`.`coupon`
(
    `id`             BIGINT      NOT NULL AUTO_INCREMENT,
    `user_id`        BIGINT      NULL,
    `coupon_type_id` BIGINT      NULL,
    `status`         VARCHAR(25) NULL,
    CONSTRAINT `coupon_PK` PRIMARY KEY (`id`),
    CONSTRAINT `coupon_FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `easyfood`.`user` (`id`),
    CONSTRAINT `coupon_FK_coupon_type_id` FOREIGN KEY (`coupon_type_id`) REFERENCES `easyfood`.`coupon_type` (`id`)
)
