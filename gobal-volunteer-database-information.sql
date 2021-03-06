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



INSERT INTO users(FIRSTNAME,LASTNAME,EMAILID,phoneNumber,PASSWORD,active,CreatedDate,createdBy,role)
VALUES ('Biruk','kadu', 'birukfekadu123@gmail.com', '+8615558797197','abc123',1,CURRENT_DATE,1,1)
=================
Phase 2
===================


alter table users alter column createddate varchar(50)

create table activityDetails(

activityId int AUTO_INCREMENT primary key,
activityName varchar(50),
activityDate varchar(50),
activityStartTime varchar(50),
activityEndTime varchar(50),
place varchar(30),
duration int (10),
content varchar (50),
totalNumberOfPeople int (6),
academicScore int (3),
createdBy int,
status bit default(0),
createdDate varchar (50),
ApprovedBy  int,
approvedDate varchar(50),
foreign key(createdBy) REFERENCES globalvolunteer.users(userId)

)

create table activityTransaction(
id int AUTO_INCREMENT primary key,
activityId int,
volunteerId int,
volunteerApplieddate varchar (50),
cancel bit default(0),
canceledDate varchar(50),
checkInDate varchar(50),
checkOuttime varchar(50),
foreign key(activityId) REFERENCES globalvolunteer.activityDetails(activityId),
foreign key (volunteerId) REFERENCES globalvolunteer.users(userId)
)

