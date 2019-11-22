CREATE TABLE IF NOT EXISTS `easyfood`.`users`
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

INSERT INTO `easyfood`.`users`
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
    CONSTRAINT `address_FK` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);