CREATE TABLE IF NOT EXISTS `easyfood`.`authorities`
(
    `name` VARCHAR(50) NOT NULL,
    CONSTRAINT authorities_PK PRIMARY KEY (`name`)
);

INSERT INTO `easyfood`.`authorities` (`name`)
VALUES ('ADMINISTRATOR'),
       ('USER');

CREATE TABLE IF NOT EXISTS `easyfood`.`user_authorities`
(
    `user_id`        BIGINT      NOT NULL,
    `authority_name` VARCHAR(50) NOT NULL,
    CONSTRAINT `user_authorities_PK` PRIMARY KEY (`user_id`, `authority_name`),
    CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `authorities`(`name`)
);

INSERT INTO `easyfood`.`user_authorities` (user_id, authority_name)
VALUES (1, 'ADMINISTRATOR'),
       (2, 'USER');
