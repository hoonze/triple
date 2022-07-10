CREATE SCHEMA IF NOT EXISTS `triple` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

use triple;

DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `review_id` varchar(45) NOT NULL,
  `user_id` varchar(45) NOT NULL,
  `value` int NOT NULL,
  `remarks` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `review_id` varchar(45) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `user_id` varchar(45) NOT NULL,
  `place_id` varchar(45) NOT NULL,
  `attached_photo_ids` json DEFAULT NULL,
  PRIMARY KEY (`review_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create index idx_placeId on review(place_id);

create index idx_userId on log(user_id);