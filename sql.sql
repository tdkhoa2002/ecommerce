-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ecommerce
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ecommerce
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ecommerce` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ecommerce` ;

-- -----------------------------------------------------
-- Table `ecommerce`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(45) NULL DEFAULT NULL,
  `ward` VARCHAR(45) NULL DEFAULT NULL,
  `city` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `phone` VARCHAR(45) NULL DEFAULT NULL,
  `full_name` VARCHAR(255) NULL DEFAULT NULL,
  `avatar` VARCHAR(255) NULL DEFAULT NULL,
  `red_flag` INT NULL DEFAULT NULL,
  `role_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 40
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`address_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`address_user` (
  `address_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`address_id`, `user_id`),
  INDEX `fk_address_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_address_has_user_address1_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_has_user_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `ecommerce`.`address` (`id`),
  CONSTRAINT `fk_address_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ecommerce`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`banner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`banner` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `category_id` INT NULL DEFAULT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_category_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `ecommerce`.`category` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`shop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`shop` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  `status` INT NULL DEFAULT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_shop_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_shop_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ecommerce`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `price` INT NULL DEFAULT NULL,
  `qty` INT NULL DEFAULT NULL,
  `thumbnail` VARCHAR(255) NULL DEFAULT NULL,
  `sold` INT NULL DEFAULT NULL,
  `is_deleted` INT NULL DEFAULT NULL,
  `shop_id` INT NULL DEFAULT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_shop1_idx` (`shop_id` ASC) VISIBLE,
  INDEX `fk_product_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `ecommerce`.`category` (`id`),
  CONSTRAINT `fk_product_shop1`
    FOREIGN KEY (`shop_id`)
    REFERENCES `ecommerce`.`shop` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_images_product_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_images_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `ecommerce`.`product` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 71
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`payment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`voucher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`voucher` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `code` VARCHAR(45) NULL DEFAULT NULL,
  `value` INT NULL DEFAULT NULL,
  `is_deleted` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`order1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`order1` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total_amount` DOUBLE NULL DEFAULT NULL,
  `created_time` DATETIME NULL DEFAULT NULL,
  `user_id` INT NOT NULL,
  `payment_id` INT NULL DEFAULT NULL,
  `voucher_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_order_payment_method1_idx` (`payment_id` ASC) VISIBLE,
  INDEX `fk_order_voucher1_idx` (`voucher_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_payment_method1`
    FOREIGN KEY (`payment_id`)
    REFERENCES `ecommerce`.`payment` (`id`),
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ecommerce`.`user` (`id`),
  CONSTRAINT `fk_order_voucher1`
    FOREIGN KEY (`voucher_id`)
    REFERENCES `ecommerce`.`voucher` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`orderdetail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`orderdetail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NULL DEFAULT NULL,
  `shop_id` INT NOT NULL,
  `status` INT NULL DEFAULT NULL,
  `create_time` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_has_product_product1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_order_has_product_order1_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_orderdetail_shop1_idx` (`shop_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_has_product_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `ecommerce`.`order1` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_order_has_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `ecommerce`.`product` (`id`),
  CONSTRAINT `fk_orderdetail_shop1`
    FOREIGN KEY (`shop_id`)
    REFERENCES `ecommerce`.`shop` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ecommerce`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `content` LONGTEXT NULL DEFAULT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  `star` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_product_has_user_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_has_user_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `ecommerce`.`product` (`id`),
  CONSTRAINT `fk_product_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ecommerce`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
