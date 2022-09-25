-- script to insert initial sample data

-- insert sample Users
INSERT INTO SCHEMA1.USERS (ID_, LOGIN_NAME_, FIRST_NAME_, LAST_NAME_) VALUES (1, 'hubert.dostal', 'Hubert', 'Dostal');
INSERT INTO SCHEMA1.USERS (ID_, LOGIN_NAME_, FIRST_NAME_, LAST_NAME_) VALUES (2, 'john.doe', 'John', 'Doe');
INSERT INTO SCHEMA1.USERS (ID_, LOGIN_NAME_, FIRST_NAME_, LAST_NAME_) VALUES (3, 'jane.mcdonald', 'Jane', 'McDonald');

-- insert sample Tasks
INSERT INTO SCHEMA2.TASKS (ID_, USER_NOTE_, TASK_DATA_, ACQUIRED_BY_, CREATED_BY_) VALUES (1, 'REST controller to be created using Spring Boot', 'Create sample REST controller serving simple task database', 2, 1);
INSERT INTO SCHEMA2.TASKS (ID_, USER_NOTE_, TASK_DATA_, ACQUIRED_BY_, CREATED_BY_) VALUES (2, 'Write documentation to REST controller once done', 'Write documentation to REST controller once done and place it to Sharepoint', 3, 1);
