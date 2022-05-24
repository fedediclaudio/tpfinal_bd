CREATE DATABASE if NOT EXISTS tpfinal_bd;
CREATE USER 'tpfinal_user'@'%' IDENTIFIED BY 'password';
GRANT ALL ON tpfinal_bd.* TO 'tpfinal_user'@'%';