CREATE DATABASE IF NOT EXISTS db_cz_cms DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use mysql;
insert into mysql.user (Host,User,Password,ssl_cipher,x509_issuer,x509_subject) values("%","db_cz_cms",password("db_cz_cms!@#321"),'','','');
flush privileges;

grant all privileges on db_cz_cms.* to 'db_cz_cms'@'%' identified by 'db_cz_cms!@#321';
grant all privileges on db_cz_cms.* to 'db_cz_cms'@'localhost' identified by 'db_cz_cms!@#321';
grant all privileges on db_cz_cms.* to 'db_cz_cms'@'127.0.0.1' identified by 'db_cz_cms!@#321';

flush privileges;
