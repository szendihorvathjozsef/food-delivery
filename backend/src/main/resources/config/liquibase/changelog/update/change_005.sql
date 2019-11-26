INSERT INTO `easyfood`.`item_type` (`name`)
    VALUES ('Leves'), ('Főétel'), ('Tészta'), ('Desszert');

INSERT INTO `easyfood`.`item` (`id`, `name`, `price`, `kcal`, `protein`, `fat`, `carbs`, `item_type_name`)
VALUES (1, 'Milánói spagetti', 1500, 156, 10, 10, 'Tészta'),
       (2, 'Újházi tyúkhúsleves', 800, 100, 12, 12, 'Leves'),
       (3, 'Aranygaluska', 650, 100, 12, 12, 'Desszert');

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