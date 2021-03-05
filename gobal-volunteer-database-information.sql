CREATE SCHEMA globalvolunteer;

create table roles(
roleId int not null AUTO_INCREMENT Primary key,
roleName varchar(20)
)


INSERT INTO roles(rolename) VALUES('System Administrator');
INSERT INTO roles(rolename) VALUES('Activity Manager');
INSERT INTO roles(rolename) VALUES('Volunteers');


Create table users(
userId int AUTO_INCREMENT Primary key,
firstName varchar(15),
lastName varchar(15) NOT NULL,
emailId varchar(40),
phoneNumber varchar(15),
password varchar(15),
active bit default(1),
createdDate varchar(20),
createdBy int,
role int not null,
FOREIGN KEY (role) REFERENCES globalvolunteer.roles(roleId)
)


alter table users alter column createddate varchar(50)

INSERT INTO users(FIRSTNAME,LASTNAME,EMAILID,phoneNumber,PASSWORD,active,CreatedDate,createdBy,role)
VALUES ('Biruk','kadu', 'birukfekadu123@gmail.com', '+8615558797197','abc123',1,CURRENT_DATE,1,1)