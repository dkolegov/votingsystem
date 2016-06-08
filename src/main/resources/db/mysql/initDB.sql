CREATE DATABASE IF NOT EXISTS votingsystem;
GRANT ALL PRIVILEGES ON votingsystem.* TO pc@localhost IDENTIFIED BY 'pc';

USE votingsystem;

CREATE TABLE IF NOT EXISTS restaurants (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS dishes (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  price DECIMAL(19,2),
  restaurantId INT(4) UNSIGNED NOT NULL,
  FOREIGN KEY (restaurantId) REFERENCES restaurants(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS votes (
  userId VARCHAR(255),
  votedate DATE,
  votetime TIME,
  restaurantId INT(4) UNSIGNED NOT NULL,
  FOREIGN KEY (restaurantId) REFERENCES restaurants(id),
  UNIQUE (userId, votedate)
) engine=InnoDB;