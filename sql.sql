/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - insurance_prediction
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`insurance_prediction` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `insurance_prediction`;

/*Table structure for table `agent` */

DROP TABLE IF EXISTS `agent`;

CREATE TABLE `agent` (
  `agent_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `pin` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`agent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `agent` */

insert  into `agent`(`agent_id`,`login_id`,`fname`,`lname`,`gender`,`place`,`pin`,`email`,`phone`) values 
(1,3,'san','kar','male','kochi','688523','safasfssfd@gmail.com','6238526459'),
(2,4,'Reshmas','Kamaths','female','kochi','682035','student@gmail.com','8078770123'),
(3,5,'Renuka Kamath','Renuka Kamath','male','fjji','123456','renukakamath2@gmail.com','1234567890');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `agent_id` int(11) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`agent_id`,`complaint`,`reply`,`date`) values 
(1,1,'worst piza ever','okda','2023-01-30'),
(2,1,'worst piza ever','okay','2023-01-31'),
(3,3,'bbggd','pending','2023-05-24'),
(4,3,'bbggd','pending','2023-05-24');

/*Table structure for table `damagerequest` */

DROP TABLE IF EXISTS `damagerequest`;

CREATE TABLE `damagerequest` (
  `damagerequest_id` int(11) NOT NULL AUTO_INCREMENT,
  `agent_id` int(11) DEFAULT NULL,
  `policyrequest_id` int(11) DEFAULT NULL,
  `vehicleimage` varchar(1000) DEFAULT NULL,
  `price` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `vehicleimage1` varchar(1000) DEFAULT NULL,
  `vehicleimage2` varchar(1000) DEFAULT NULL,
  `vehicleimage3` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`damagerequest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `damagerequest` */

insert  into `damagerequest`(`damagerequest_id`,`agent_id`,`policyrequest_id`,`vehicleimage`,`price`,`date`,`status`,`vehicleimage1`,`vehicleimage2`,`vehicleimage3`) values 
(1,3,4,'static/uploads/d75cf8ce-2f52-4149-bf57-dfd8d0a95ab6abc.jpg','28000','2023-05-24','pending','static/uploads/8dee6fc4-0174-49c9-965b-e8d15a395043abc.jpg','static/uploads/3f284edb-762e-4043-a34c-1e2c72d31e14abc.jpg','static/uploads/2356498d-17d5-46bc-af68-a4bf0595e3e9abc.jpg');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(3,'aj','aj','agent'),
(4,'resh','resh@123','agent'),
(5,'hai','hai','agent');

/*Table structure for table `policy` */

DROP TABLE IF EXISTS `policy`;

CREATE TABLE `policy` (
  `policy_id` int(11) NOT NULL AUTO_INCREMENT,
  `policy` varchar(100) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `no_ofdays` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`policy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `policy` */

insert  into `policy`(`policy_id`,`policy`,`amount`,`no_ofdays`) values 
(2,'super','50','100');

/*Table structure for table `policyrequest` */

DROP TABLE IF EXISTS `policyrequest`;

CREATE TABLE `policyrequest` (
  `policyrequest_id` int(11) NOT NULL AUTO_INCREMENT,
  `agent_id` int(11) DEFAULT NULL,
  `policy_id` int(11) DEFAULT NULL,
  `vechilenum` varchar(100) DEFAULT NULL,
  `modelnum` varchar(100) DEFAULT NULL,
  `enginenum` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`policyrequest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

/*Data for the table `policyrequest` */

insert  into `policyrequest`(`policyrequest_id`,`agent_id`,`policy_id`,`vechilenum`,`modelnum`,`enginenum`,`date`,`status`) values 
(1,1,2,'2133333','2111111111','3212133','2023-01-30','Approved'),
(3,1,2,'kl 07 by 3998','1234','1234','2023-02-08','Approved'),
(4,1,2,'kl 07 by 3998','1234','1234','2023-02-08','Approved'),
(5,1,2,'kl 07 by 3694','123467','1234','2023-02-08','Approved'),
(6,1,2,'kl 07 by 3998','1234','1234','2023-02-08','Approved'),
(7,1,2,'kl 07 by 3998','123467','12345','2023-05-17','Rejected'),
(8,5,2,'kl55Hwj5192','ghs5292','526as522','2023-05-24','pending'),
(9,5,2,'kl55Hwj5192','ghs5292','526as522','2023-05-24','pending'),
(10,5,2,'kl55Hwj5192','ghs5292','526as522','2023-05-24','pending'),
(11,5,2,'kl55Hwj5192','ghs5292','526as522','2023-05-24','pending'),
(12,5,2,'kl55Hwj5192','ghs5292','526as522','2023-05-24','pending'),
(13,5,2,'kl55Hwj5192','ghs5292','526as522','2023-05-24','pending');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
