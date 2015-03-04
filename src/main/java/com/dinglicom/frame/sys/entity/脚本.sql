drop database duoxian;
create database duoxian DEFAULT CHARSET=utf8;
create table sys_account (
 id bigint not null auto_increment,
 account varchar(30),
 nickname varchar(30),
 password varchar(60),
 salt varchar(60),
 zh_state varchar(4),
 user_info_id bigint,
 sign_delete bit,
 createdate datetime,
 primary key (id)
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table sys_token (
 id bigint not null auto_increment,
 token varchar(80),
 begintime bigint,
 role_name varchar(30),
 user_account_id bigint,
 user_info_id bigint,
 sign_delete bit,
 createdate datetime,
 primary key (id)
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table sys_oranizagion (
 id bigint not null auto_increment,
 address varchar(300),
 code varchar(10),
 description varchar(100),
 name varchar(120),
 phone varchar(20),
 postcode varchar(10),
 sys_state varchar(4),
 type varchar(4),
 parent_id bigint,
 user_id bigint,
 sign_delete bit,
 createdate datetime,
 responsible_man varchar(30),
 responsible_phone varchar(20),
 primary key (id)
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table sys_permissions (
 id bigint not null auto_increment,
 createdate datetime,
 sign_delete bit,
 available bit,
 description varchar(100),
 permission varchar(30),
 primary key (id)
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table sys_roles (
 id bigint not null auto_increment,
 createdate datetime,
 sign_delete bit,
 available bit,
 description varchar(100),
 role_name varchar(30),
 primary key (id)
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table sys_roles_permissions (
 role_id bigint not null,
 permission_id bigint not null
) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table sys_user_info (
 id bigint not null auto_increment,
 realname varchar(30),
 nickname varchar(30),
 phone varchar(20),
 sex varchar(4),
 birthday datetime,
 description varchar(120),
 doc_type varchar(4),
 id_number varchar(30),
 mail varchar(50),
 provincename varchar(20),
 cityname varchar(20),
 region varchar(50),
 province_id bigint,
 city_id bigint,
 region_id bigint,
 address varchar(300),
 user_type varchar(10),
 org_id bigint,
 orgname varchar(120),
 sign_delete bit,
 createdate datetime,
 primary key (id)
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table sys_users_roles (
 account_id bigint not null,
 role_id bigint not null
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;

alter table sys_users_roles add constraint FK_laitchy0659so1fu7j7841p3s foreign key (role_id) references sys_roles (id);
alter table sys_users_roles add constraint FK_a5m1uktgte65wq3fmhr38ipwf foreign key (account_id) references sys_account (id);
alter table sys_account add constraint FK_6maort1v85bkf7p9h5mn7w14j foreign key (user_info_id) references sys_user_info (id);
alter table sys_token add constraint FK_1iuwjehr85bkf7p9h5mn7w14j foreign key (user_info_id) references sys_user_info (id);
alter table sys_token add constraint FK_2iujhjdsdiuhjjjucimn7w14j foreign key (user_account_id) references sys_account (id);
alter table sys_oranizagion add constraint FK_mblnkh9ug8upgrll9kgfd4j5p foreign key (parent_id) references sys_oranizagion (id);
alter table sys_oranizagion add constraint FK_f91m9sk5wr6dj6nv4nhd65c9s foreign key (user_id) references sys_user_info (id);
alter table sys_user_info add constraint FK_c3pa2rvs6fvdikb5jmg4p1adl foreign key (org_id) references sys_oranizagion (id);
alter table sys_roles_permissions add constraint FK_objp5mugn62ggv29nab610h68 foreign key (permission_id) references sys_permissions (id);
alter table sys_roles_permissions add constraint FK_bqbem4vxu9g1o6ofsdnbafbiw foreign key (role_id) references sys_roles (id);



create table user_product (
 id bigint not null auto_increment,
 name varchar(120),
 weight DECIMAL(10, 2),
 specification varchar(60),
 model varchar(60),
 brand varchar(30),
 price DECIMAL(10, 4),
 home_price DECIMAL(10, 4),
 type_desc varchar(4),
 short_desc varchar(120),
 details varchar(300),
 small_pic varchar(120),
 big_pic varchar(120),
 p_state varchar(4),
 productnum int;
 sign_delete bit,
 createdate datetime,
 primary key (id)
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table user_address (
 id bigint not null auto_increment,
 user_id bigint,
 name varchar(30),
 mobile_phone varchar(20),
 fixed_phone varchar(20),
 mail varchar(60),
 province varchar(30),
 city varchar(30),
 region varchar(30),
 address varchar(120),
 sign_delete bit,
 createdate datetime,
 primary key (id)
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;
alter table user_address add constraint FK_521ort1v85bkf7p9h5mn7w14j foreign key (user_id) references sys_user_info (id);



create table user_order (
 id bigint not null auto_increment,
 order_no varchar(20),
 address_id bigint,
 org_id bigint,
 orgmame varchar(120),
 user_id bigint,
 consigneename varchar(30),
 consigneephone varchar(30),
 consigneeaddress varchar(300),
 order_desc varchar(200),
 order_origin varchar(4),
 user_proxy bit,
 user_pay bit,
 order_state varchar(4),
 continueorderno  varchar(20),
 delivery_id bigint,
 deliverymame varchar(120),
 sign_delete bit,
 createdate datetime,
 primary key (id)
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;
alter table user_order add constraint FK_orderuser1v85bkf7p9h5mn7w14j foreign key (user_id) references sys_user_info (id);
alter table user_order add constraint FK_orderorg21v85bkf7p9h5mn7w14j foreign key (org_id) references sys_oranizagion (id);
alter table user_order add constraint FK_orderaddressaddressp9h5mn7w1 foreign key (address_id) references user_address (id);


create table order_item (
 id bigint not null auto_increment,
 order_no varchar(20),
 order_id bigint,
 product_id bigint,
 product_price DECIMAL(10, 4),
 product_total_price DECIMAL(10, 4),
 distribution_num int,
 distribution_target varchar(4),
 distribution_type varchar(4),
 distribution_period int,
 first_distribution_date datetime,
 productnum int,
 currentnum int,
 productname varchar(120),
 productsmallpic varchar(120),
 itemstate varchar(4),
 sign_delete bit,
 createdate datetime,
 primary key (id)
 ) ENGINE= InnoDB DEFAULT CHARSET=utf8;
alter table order_item add constraint FK_orderitemorder234p9h5mn7w14j foreign key (order_id) references user_order (id);
alter table order_item add constraint FK_orderproduct3useraddressp9h5 foreign key (product_id) references user_product (id);


create table dispatching_record (
 id BIGINT not null auto_increment,
 f_year SMALLINT UNSIGNED,
 f_quarter TINYINT UNSIGNED,
 f_month TINYINT UNSIGNED,
 f_day  TINYINT UNSIGNED,
 order_no VARCHAR(20),
 order_id BIGINT,
 product_id BIGINT,
 order_item_id BIGINT,
 product_name VARCHAR(120),
 product_small_pic VARCHAR(120),
 dispatching_num INT,
 product_price DECIMAL(10, 4),
 dispatching_target VARCHAR(4),
 dispatching_type VARCHAR(4),
 consignee_id BIGINT,
 consignee_name VARCHAR(30),
 phone VARCHAR(30),
 address VARCHAR(300),
 dispatching_org_id BIGINT,
 dispatching_org_name VARCHAR(120),
 dispatching_worker_id BIGINT,
 dispatching_norker_name VARCHAR(30),
 dispatching_norker_phone VARCHAR(30),
 confirm_num INT,
 confirm_name VARCHAR(30),
 confirm_date DATETIME,
 delivery_id bigint,
 deliverymame varchar(120),
 sign_delete BIT,
 createdate DATETIME,
 primary key (id)
) ENGINE= InnoDB DEFAULT CHARSET=utf8;
alter table dispatching_record add constraint FK_dispatchingrecorduorder7w14j foreign key (order_id) references user_order (id);
alter table dispatching_record add constraint FK_dispatchingrecordorderitemh5 foreign key (order_item_id) references order_item (id);
alter table dispatching_record add constraint FK_dispatchingrecordconsignee4j foreign key (consignee_id) references sys_user_info (id);
alter table dispatching_record add constraint FK_dspatchrecorddispatchingorg5 foreign key (dispatching_org_id) references sys_oranizagion (id);
alter table dispatching_record add constraint FK_dispatchrecorddispatchworker foreign key (dispatching_worker_id) references sys_user_info (id);


create table order_change_record (
 id BIGINT not null auto_increment,
 order_no VARCHAR(20),
 order_id BIGINT,
 f_state INT,
 begin_time DATETIME,
 end_time DATETIME,
 pause_day INT,
 f_desc VARCHAR(80),
 opt_user_id BIGINT,
 opt_user_name VARCHAR(30),
 sign_delete BIT,
 createdate DATETIME,
 primary key (id)
) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table report_subscribe_number (
 id BIGINT not null auto_increment,
 f_year SMALLINT UNSIGNED,
 f_quarter TINYINT UNSIGNED,
 f_month TINYINT UNSIGNED,
 f_day  TINYINT UNSIGNED,
 org_id BIGINT,
 org_name VARCHAR(120),
 product_id BIGINT,
 product_name VARCHAR(120),
 distrute_num INT,
 plus_num INT,
 minus_num INT,
 report_state VARCHAR(10),
 reportman_id BIGINT,
 report_name VARCHAR(30),
 sign_delete BIT,
 createdate DATETIME,
 report_num BIGINT(11),
 everyday_org_id BIGINT(20),
 primary key (id)
) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table about_us (
 id BIGINT not null auto_increment,
 order_tel VARCHAR(30),
 complaint_tel VARCHAR(30),
 official_site VARCHAR(120),
 order_site VARCHAR(120),
 wechat VARCHAR(80),
 email VARCHAR(80),
 address VARCHAR(390),
 sign_delete BIT,
 createdate DATETIME,
 primary key (id)
) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table feed_back (
 id BIGINT not null auto_increment,
 feed_id BIGINT,
 feed_name VARCHAR(30),
 feed_tel VARCHAR(30),
 content VARCHAR(600),
 feedtime DATETIME,
 feedback BIT,
 backuser_id BIGINT,
 back_username VARCHAR(30),
 back_content VARCHAR(600),
 backtime DATETIME,
 sign_delete BIT,
 createdate DATETIME,
 primary key (id)
) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table report_number_time (
 id BIGINT not null auto_increment,
 f_time VARCHAR(30),
 sign_delete BIT,
 createdate DATETIME,
 primary key (id)
) ENGINE= InnoDB DEFAULT CHARSET=utf8;

create table everyday_everyorg_report (
 id BIGINT not null auto_increment,
 f_year SMALLINT UNSIGNED,
 f_quarter TINYINT UNSIGNED,
 f_month TINYINT UNSIGNED,
 f_day  TINYINT UNSIGNED,
 report_no VARCHAR(30),
 report_state VARCHAR(30),
 total_num INT,
 report_time DATETIME,
 org_id BIGINT,
 org_name VARCHAR(120),
 org_type VARCHAR(10),
 responsible_man VARCHAR(30),
 responsible_phone VARCHAR(30),
 ship_time DATETIME,
 ship_num INT,
 ship_name VARCHAR(10),
 ship_phone VARCHAR(10),
 sign_delete BIT,
 createdate DATETIME,
 primary key (id)
) ENGINE= InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `report_number_time` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_time` varchar(30) DEFAULT NULL,
  `sign_delete` bit(1) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ENGINE= InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `down_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(150) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `receiver` varchar(60) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `pushername` varchar(50) DEFAULT NULL,
  `pusher_id` bigint(20) DEFAULT NULL,
  `send_num` int,
  `success_num` int,
  `delete_num` int,
  `sign_delete` bit(1) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ENGINE= InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `app_update` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` varchar(30) DEFAULT NULL,
  `versioncode` INT,
  `info` varchar(2000) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `url` varchar(120) DEFAULT NULL,
  `forceupdate` bit(1) DEFAULT NULL,
  `sign_delete` bit(1) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ENGINE= InnoDB DEFAULT CHARSET=utf8;



create table continue_order_push (
 id BIGINT not null auto_increment,
 days INT,
 sign_delete BIT,
 createdate DATETIME,
 primary key (id)
) ENGINE= InnoDB DEFAULT CHARSET=utf8;