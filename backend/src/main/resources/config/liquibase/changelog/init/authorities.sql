CREATE TABLE `easyfood`.`authorities` (
    `name` VARCHAR(50) NOT NULL,
    CONSTRAINT authorities_PK PRIMARY KEY (`name`)
);

INSERT INTO `easyfood`.`authorities` (`name`)
VALUES ('ADMINISTRATOR'), ('USER');