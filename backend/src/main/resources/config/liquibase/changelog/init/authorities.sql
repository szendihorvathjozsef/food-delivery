CREATE TABLE `easyfood`.`authorities` (
    `name` VARCHAR(50) NOT NULL,
    CONSTRAINT authorities_PK PRIMARY KEY (`name`)
);

CREATE TABLE `easyfood`,`user_authorities` (
    `user_id` BIGINT NOT NULL,
    `authority_name` VARCHAR(50) NOT NULL,
    CONSTRAINT authority_name_PK PRIMARY KEY (`au`)
)

INSERT INTO `easyfood`.`authorities` (`name`)
VALUES ('ADMINISTRATOR'), ('USER');