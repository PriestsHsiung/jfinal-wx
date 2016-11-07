/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.20 : Database - heyuo_qy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `consult_records` */

DROP TABLE IF EXISTS `consult_records`;

CREATE TABLE `consult_records` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `from_user` varchar(20) NOT NULL,
  `to_user` varchar(20) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` varchar(20) NOT NULL,
  `content` varchar(5000) NOT NULL,
  `forbidden` tinyint(1) NOT NULL,
  `send_success` tinyint(1) NOT NULL,
  `msg_id` varchar(50) NOT NULL,
  `crawl` tinyint(1) DEFAULT '0',
  `crawl_error_msg` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Table structure for table `msg_media` */

DROP TABLE IF EXISTS `msg_media`;

CREATE TABLE `msg_media` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `msg_id` varchar(20) NOT NULL,
  `file_path` varchar(500) NOT NULL,
  `url_path` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qy_level` */

DROP TABLE IF EXISTS `qy_level`;

CREATE TABLE `qy_level` (
  `qy` varchar(60) NOT NULL COMMENT '企业名称',
  `wx_user` varchar(60) NOT NULL COMMENT '企业用户微信号',
  `level` int(30) NOT NULL COMMENT '缴费档次：1，2,3,4档',
  PRIMARY KEY (`wx_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
