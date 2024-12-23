-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           8.0.30 - MySQL Community Server - GPL
-- SE du serveur:                Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Listage de la structure de la base pour gozen_service
CREATE DATABASE IF NOT EXISTS `gozen_service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gozen_service`;

-- Listage de la structure de table gozen_service. company
CREATE TABLE IF NOT EXISTS `company` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `company_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `logo` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. conversation
CREATE TABLE IF NOT EXISTS `conversation` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. cv
CREATE TABLE IF NOT EXISTS `cv` (
  `experience_points` int DEFAULT NULL,
  `upload_date` date DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq1uw3pyveaumh2hq2md9istwe` (`user_id`),
  CONSTRAINT `FKq1uw3pyveaumh2hq2md9istwe` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. education
CREATE TABLE IF NOT EXISTS `education` (
  `end_date` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `start_date` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `cv_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `degree` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `field_of_study` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `institution` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkd5tom7k9kk1wuk0tvfpex7s9` (`cv_id`),
  CONSTRAINT `FKkd5tom7k9kk1wuk0tvfpex7s9` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. employers
CREATE TABLE IF NOT EXISTS `employers` (
  `activity` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sector` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKnnl4ba0tc831e25ufip4ek2yq` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. experience
CREATE TABLE IF NOT EXISTS `experience` (
  `end_date` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `start_date` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `cv_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `company` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `position` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1d85i1ne8p5sqtfuglhr0p8qj` (`cv_id`),
  CONSTRAINT `FK1d85i1ne8p5sqtfuglhr0p8qj` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. interest
CREATE TABLE IF NOT EXISTS `interest` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `cv_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKllloue5oxy04upayb9yx7ae5v` (`cv_id`),
  CONSTRAINT `FKllloue5oxy04upayb9yx7ae5v` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. job_application
CREATE TABLE IF NOT EXISTS `job_application` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `applicant_email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `applicant_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `job_offer_id` bigint NOT NULL,
  `additional_skills` varbinary(255) DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `job_seeker_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd6kb99y5xttecufytftbvn529` (`job_offer_id`),
  KEY `FK69ar68336gwwbvunlxvtnd0rh` (`job_seeker_id`),
  CONSTRAINT `FK69ar68336gwwbvunlxvtnd0rh` FOREIGN KEY (`job_seeker_id`) REFERENCES `job_seekers` (`id`),
  CONSTRAINT `FKd6kb99y5xttecufytftbvn529` FOREIGN KEY (`job_offer_id`) REFERENCES `job_offer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. job_application_additional_skills
CREATE TABLE IF NOT EXISTS `job_application_additional_skills` (
  `job_application_id` bigint NOT NULL,
  `additional_skills` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  KEY `FKj79okr9ctt1tghcm1dolpilup` (`job_application_id`),
  CONSTRAINT `FKj79okr9ctt1tghcm1dolpilup` FOREIGN KEY (`job_application_id`) REFERENCES `job_application` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. job_offer
CREATE TABLE IF NOT EXISTS `job_offer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `description` longtext COLLATE utf8mb4_general_ci,
  `salary` double NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `employer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKntaloqmnxptgh9k8qnqy47atw` (`employer_id`),
  CONSTRAINT `FKntaloqmnxptgh9k8qnqy47atw` FOREIGN KEY (`employer_id`) REFERENCES `employers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. job_offer_missions
CREATE TABLE IF NOT EXISTS `job_offer_missions` (
  `job_offer_id` bigint NOT NULL,
  `missions` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  KEY `FKq0pn2551o0kh18agj3ow7lq1x` (`job_offer_id`),
  CONSTRAINT `FKq0pn2551o0kh18agj3ow7lq1x` FOREIGN KEY (`job_offer_id`) REFERENCES `job_offer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. job_proposal
CREATE TABLE IF NOT EXISTS `job_proposal` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `status` enum('ACCEPTED','PENDING','REJECTED') COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `job_offer_id` bigint NOT NULL,
  `job_seeker_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjicn3yeeg4btvsl6pp852eob2` (`job_offer_id`),
  KEY `FK6q4scto7chnu0r00w3go70qpg` (`job_seeker_id`),
  CONSTRAINT `FK6q4scto7chnu0r00w3go70qpg` FOREIGN KEY (`job_seeker_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjicn3yeeg4btvsl6pp852eob2` FOREIGN KEY (`job_offer_id`) REFERENCES `job_offer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. job_seekers
CREATE TABLE IF NOT EXISTS `job_seekers` (
  `id` bigint NOT NULL,
  `cv_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK7781suapydgwhcwxlp37tr8ke` (`cv_id`),
  CONSTRAINT `FK6n38u8vdenycx0vm27sqy2jqn` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKok82fhev6k8s66ii9v4ycx4wv` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. message
CREATE TABLE IF NOT EXISTS `message` (
  `conversation_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sender_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6yskk3hxw5sklwgi25y6d5u1l` (`conversation_id`),
  KEY `FKbi5avhe69aol2mb1lnm6r4o2p` (`sender_id`),
  CONSTRAINT `FK6yskk3hxw5sklwgi25y6d5u1l` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`),
  CONSTRAINT `FKbi5avhe69aol2mb1lnm6r4o2p` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. performance_dashboard
CREATE TABLE IF NOT EXISTS `performance_dashboard` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `active_user_stats` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi5xv2py9sbxyxgh77uef7byxw` (`user_id`),
  CONSTRAINT `FKi5xv2py9sbxyxgh77uef7byxw` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. permission
CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. point_system
CREATE TABLE IF NOT EXISTS `point_system` (
  `points` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `cv_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `source` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpwwv1uhdk2jvtu2ltgpdsw2wn` (`cv_id`),
  CONSTRAINT `FKpwwv1uhdk2jvtu2ltgpdsw2wn` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. profile_import
CREATE TABLE IF NOT EXISTS `profile_import` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `source` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2c2obblgg1paf5k88olc2p5kr` (`user_id`),
  CONSTRAINT `FK2c2obblgg1paf5k88olc2p5kr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. profile_recommendation
CREATE TABLE IF NOT EXISTS `profile_recommendation` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `criteria` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKimej59dk98hs06y7omf53q39h` (`user_id`),
  CONSTRAINT `FKimej59dk98hs06y7omf53q39h` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. reference
CREATE TABLE IF NOT EXISTS `reference` (
  `created_at` datetime(6) DEFAULT NULL,
  `cv_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `contact_info` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `relation` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb5rwlmfhpupc8gcokhvj617vg` (`cv_id`),
  CONSTRAINT `FKb5rwlmfhpupc8gcokhvj617vg` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. role_permissions
CREATE TABLE IF NOT EXISTS `role_permissions` (
  `permission_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`permission_id`,`role_id`),
  KEY `FKlodb7xh4a2xjv39gc3lsop95n` (`role_id`),
  CONSTRAINT `FKh0v7u4w7mttcu81o8wegayr8e` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `FKlodb7xh4a2xjv39gc3lsop95n` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. saved_job_offer
CREATE TABLE IF NOT EXISTS `saved_job_offer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `saved_at` datetime(6) NOT NULL,
  `job_offer_id` bigint NOT NULL,
  `job_seeker_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkdk1x8cr9cv1vbl2wto8iwsr6` (`job_offer_id`),
  KEY `FK77t7iryfd328gi70bt2jwox4e` (`job_seeker_id`),
  CONSTRAINT `FK77t7iryfd328gi70bt2jwox4e` FOREIGN KEY (`job_seeker_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKkdk1x8cr9cv1vbl2wto8iwsr6` FOREIGN KEY (`job_offer_id`) REFERENCES `job_offer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. skill
CREATE TABLE IF NOT EXISTS `skill` (
  `years_of_experience` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `cv_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `level` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKli4yjkukgrskb6f8uywgkgjdq` (`cv_id`),
  CONSTRAINT `FKli4yjkukgrskb6f8uywgkgjdq` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. social
CREATE TABLE IF NOT EXISTS `social` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `icon_svg` longtext COLLATE utf8mb4_general_ci,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `cv_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK134hnncqvjkdxjk9vd2v3nenv` (`cv_id`),
  CONSTRAINT `FK134hnncqvjkdxjk9vd2v3nenv` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. statistics
CREATE TABLE IF NOT EXISTS `statistics` (
  `interactions` int NOT NULL,
  `points` int NOT NULL,
  `views` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `cv_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKo29ataktfm8njeivhbx3g5e2y` (`cv_id`),
  CONSTRAINT `FK8qdjxyice0wki7bhheajybv0h` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. task
CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `experience_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1wu5jn43yfn8q11lx6cb4j20o` (`experience_id`),
  CONSTRAINT `FK1wu5jn43yfn8q11lx6cb4j20o` FOREIGN KEY (`experience_id`) REFERENCES `experience` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de table gozen_service. users
CREATE TABLE IF NOT EXISTS `users` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `profile_photo_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `surname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `password_reset_token` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `token_expiration_date` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FK4qu1gr772nnf6ve5af002rwya` (`role_id`),
  CONSTRAINT `FK4qu1gr772nnf6ve5af002rwya` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Les données exportées n'étaient pas sélectionnées.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
