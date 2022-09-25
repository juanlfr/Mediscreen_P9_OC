-- -----------------------------------------------------
-- Schema mediscreen
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mediscreen` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mediscreen` ;

-- -----------------------------------------------------
-- Table `mediscreen`.`hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mediscreen`.`hibernate_sequence` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
-- -----------------------------------------------------
-- Table `mediscreen`.`patient_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mediscreen`.`patient_info` (
  `id` BIGINT NOT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `date_of_birth` DATE NULL DEFAULT NULL,
  `first_name` VARCHAR(255) NULL DEFAULT NULL,
  `gender` VARCHAR(255) NULL DEFAULT NULL,
  `last_name` VARCHAR(255) NULL DEFAULT NULL,
  `phone` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
TRUNCATE TABLE patient_info;
INSERT INTO patient_info VALUES ('1','745 West Valley Farms Drive','1952-09-27','Pippa','F','Rees','628-423-0993');
INSERT INTO patient_info VALUES ('2','2 Warren Street','1968-06-22','Lucas','M','Ferguson','387-866-1399');
INSERT INTO patient_info VALUES ('3','599 East Garden Ave','1952-11-11','Edward','M','Arnold','123-727-2779');
INSERT INTO patient_info VALUES ('4','894 Hall Street','1946-11-26','Anthony','M','Sharp','451-761-8383');
INSERT INTO patient_info VALUES ('5','4 Southampton Road','1958-06-29','Wendy','F','Ince','802-911-9975');
INSERT INTO patient_info VALUES ('6','40 Sulphur Springs Dr','1949-12-07','Tracey','F','Ross','131-396-5049');
INSERT INTO patient_info VALUES ('7','12 Cobblestone St','1966-12-31','Claire','F','Wilson','300-452-1091');
INSERT INTO patient_info VALUES ('8','193 Vale St','1945-06-24','Max','M','Buckland','833-534-0864');
INSERT INTO patient_info VALUES ('9','12 Beechwood Road','1964-06-18','Natalie','F','Clark','241-467-9197');
INSERT INTO patient_info VALUES ('10','1202 Bumble Dr','1959-06-28','Piers','M','Bailey','747-815-0557');
