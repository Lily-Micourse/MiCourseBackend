create database if not exists micourse;

use micourse;

create table if not exists `user`
(
  `id`                          int primary key                auto_increment,
  `user_name`                   varchar(255)          not null,
  `gender`                      enum ('男', '女', '未知') not null default '未知',
  `grade`                       int                   not null default 2018,
  `school_department_id`        int,
  `password`                    varchar(255)          not null,
  `register_email`              varchar(255)          not null unique,
  `is_register_email_validated` bit(1)                not null default 0,
  `school_email`                varchar(255) unique,
  `is_school_email_validated`   bit(1)                not null default 0,
  `qq_number`                   varchar(16),
  `add_time`                    datetime              not null,
  `add_ip`                      varchar(128)          not null,
  `portrait_url`                varchar(255)          not null,
  `banned`                      bit(1)                not null default 0
)
  engine = InnoDB
  default charset = utf8;

create table if not exists `school_department`
(
  `id`   int          not null primary key,
  `name` varchar(255) not null
)
  engine = InnoDB
  default charset = utf8;

create table if not exists `email_validation`
(
  `id`              int primary key       auto_increment,
  `user_id`         int          not null,
  `validation_key`  varchar(255) not null,
  `expired`         datetime     not null,
  `validation_type` tinyint(3)   not null default 0
)
  engine = InnoDB
  default charset = utf8;

# `id` int primary key,
# `user_id` int not null,
# `validation_key` varchar (255) not null,
# `expired` datatime not null,

create table if not exists `course`
(
  `id`                   int primary key,
  `course_icon`          varchar(255) not null,
  `course_code`          varchar(32)  not null,
  `course_name`          varchar(255) not null,
  `credit`               int          not null,
  `teacher`              varchar(255),
  `course_category_id`   int          not null,
  `course_department_id` int          not null,
  `description`          text
)
  engine = InnoDB
  default charset = utf8;

create table if not exists `course_department`
(
  `id`   int primary key,
  `name` varchar(255) not null
)
  engine = InnoDB
  default charset = utf8;

create table if not exists `course_category`
(
  `id`   int primary key,
  `name` varchar(255) not null
)
  engine = InnoDB
  default charset = utf8;

create table if not exists `course_comment`
(
  `id`        int primary key                                                                                                                                                                                                auto_increment,
  `course_id` int         not null,
  `user_id`   int not null,
  `deleted`   bit(3) not null                                                                                                                                                                                            default 0,
  `useful`    int        not null                                                                                                                                                                                            default 0,
  `useless`   int        not null                                                                                                                                                                                            default 0,
  `content`   text       not null,
  `add_time`  datetime   not null,
  `semester`  varchar(16) not null
)
  engine = InnoDB
  default charset = utf8;

create table if not exists `course_feedback`
(
  `id`                    int primary key                        auto_increment,
  `course_id` int                            not null,
  `user_id`   int not null,
  `level`     tinyint(3) not null,
  `pressure`  enum ('low', 'medium', 'high') not null,
  `score`     tinyint(3)                     not null,
  `eval_paper` tinyint(1)                    not null default 0,
  `eval_attendance` tinyint(1)               not null      default 0,
  `eval_teamwork`   tinyint(1)               not null      default 0,
  `eval_closed_book_exam` tinyint(1)         not null            default 0,
  `eval_open_book_exam`   tinyint(1)         not null            default 0,
  `eval_others`           tinyint(1)         not null            default 0
)
  engine = InnoDB
  default charset = utf8;

create table if not exists `course_tag_set`
(
  `id`  int primary key auto_increment,
  `tag` varchar(32) not null
)
  engine = InnoDB
  default charset = utf8;

create table if not exists `course_tag`
(
  `id`        int primary key auto_increment,
  `course_id` int not null,
  `tag_id`    int not null
)
  engine = InnoDB
  default charset = utf8;

create table if not exists `announcement`
(
  `id`         int primary key auto_increment,
  `content` text            not null,
  `pusblisher` varchar(255) not null
)
  engine = InnoDB
  default charset = utf8;

# template
#create table if not exists 
#(
#) engine=InnoDB default charset=utf8;