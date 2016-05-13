INSERT INTO USER (id,email,first_name,last_name,password,role) VALUES (1,'banzhao.huang@sap.com','banzhao','huang','$2a$10$EhPOZsT1CAArm/JCLJtW7.4BuPHNz3BFcsFWmzZeRM4p33ee7yufa',1);
INSERT INTO USER (id,email,first_name,last_name,password,role) VALUES (2,'ultra7677@gmail.com','jacky','mao','$2a$10$EhPOZsT1CAArm/JCLJtW7.4BuPHNz3BFcsFWmzZeRM4p33ee7yufa',0);

INSERT INTO EMPLOYEE (id,email,first_name,last_name,user_id,avatarid,role) VALUES (1,'banzhao.huang@sap.com','banzhao','huang',1,0,1);
INSERT INTO EMPLOYEE (id,email,first_name,last_name,user_id,avatarid,role) VALUES (2,'ultra7677@gmail.com','jacky','mao',2,0,0);

INSERT INTO APPROVER (id,user_id) VALUES(1,1);
INSERT INTO APPROVER (id,user_id) VALUES(2,2);

INSERT INTO FILES (id,file_location,file_name,type) VALUES(0,'/Users/ultra/Documents/images/avatar.jpg','avatar.jpg','image
');

INSERT INTO COMPANY  (id,name,logo_id) VALUES(1,'SAP',0);

INSERT INTO Leave_Type (id,name,foreign_name) VALUES (1,'年假','Annual Leave');
INSERT INTO Leave_Type (id,name,foreign_name) VALUES (2,'病假（不扣薪资）','Sick Leave (Full Pay)');
INSERT INTO Leave_Type (id,name,foreign_name) VALUES (3,'病假（扣薪资）','Sick Leave)');
INSERT INTO Leave_Type (id,name,foreign_name) VALUES (4,'事假','Compassionate Leave');
INSERT INTO Leave_Type (id,name,foreign_name) VALUES (5,'婚假','Marriage Leave');
INSERT INTO Leave_Type (id,name,foreign_name) VALUES (6,'婚假（晚婚）','Late Marriage Leave');
INSERT INTO Leave_Type (id,name,foreign_name) VALUES (7,'产假','Maternity Leave');
INSERT INTO Leave_Type (id,name,foreign_name) VALUES (8,'陪产假','Paternity Leave');
INSERT INTO Leave_Type (id,name,foreign_name) VALUES (9,'换休假','Off-in-lieu');
INSERT INTO Leave_Type (id,name,foreign_name) VALUES (10,'工伤休假','On Job Injury Leave');

INSERT INTO Work_Hours (id,morning_Start,moring_End,afternoon_Start,afternoon_End) VALUES (1,9,12,13,18);
INSERT INTO Work_Day (id,monday,tuesday,wednesday,thursday,friday,saturday,sunday) VALUES (1,1,1,1,1,1,0,0);

INSERT INTO Leave_Type_Rule(id,leave_type_id,base_days,increase_days_per_year,max_days) VALUES (1,1,15,1,20);
INSERT INTO Leave_Type_Rule(id,leave_type_id,base_days,increase_days_per_year,max_days) VALUES (2,3,15,1,20);

INSERT INTO LEAVE_DAYS_INFO (id,days,year,leave_type_id,user_id) VALUES(1,15,2016,1,1);
INSERT INTO LEAVE_DAYS_INFO (id,days,year,leave_type_id,user_id) VALUES(2,15,2016,3,1);

INSERT INTO LEAVE_DAYS_INFO (id,days,year,leave_type_id,user_id) VALUES(3,15,2016,1,2);
INSERT INTO LEAVE_DAYS_INFO (id,days,year,leave_type_id,user_id) VALUES(4,15,2016,3,2);
