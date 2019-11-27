ALTER TABLE easyfood.`user` ADD created_by varchar(50) NOT NULL;
ALTER TABLE easyfood.`user` ADD created_date TIMESTAMP NULL;
ALTER TABLE easyfood.`user` ADD last_modified_by varchar(50) NULL;
ALTER TABLE easyfood.`user` ADD last_modified_date TIMESTAMP NULL;
