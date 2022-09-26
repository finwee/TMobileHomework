-- for purposes of this demo drop schemas to be sure DB is clean
DROP ALL OBJECTS;
DROP SCHEMA IF EXISTS SCHEMA1 CASCADE;
DROP SCHEMA IF EXISTS SCHEMA2 CASCADE;

-- script to create H2 database schema
CREATE SCHEMA IF NOT EXISTS SCHEMA1;
CREATE SCHEMA IF NOT EXISTS SCHEMA2;

-- Initiate native fulltext search for H2
CREATE ALIAS IF NOT EXISTS FT_INIT FOR "org.h2.fulltext.FullText.init";
CALL FT_INIT();

-- Used for generated IDs. For sample it is enough like this for sake of this app
CREATE SEQUENCE IF NOT EXISTS "HIBERNATE_SEQUENCE"
MINVALUE 1
MAXVALUE 999999999
INCREMENT BY 1
START WITH 100
NOCACHE
NOCYCLE;

-- User table
CREATE TABLE IF NOT EXISTS SCHEMA1.USERS (
  ID_ INTEGER PRIMARY KEY, -- primary index automatically created
  LOGIN_NAME_ VARCHAR(30) NOT NULL UNIQUE, -- unique among all user, unique index automatically created
  FIRST_NAME_ VARCHAR(30) NOT NULL,
  LAST_NAME_ VARCHAR(30) NOT NULL
);

-- Task table
CREATE TABLE IF NOT EXISTS SCHEMA2.TASKS (
  ID_ INTEGER PRIMARY KEY, -- primary index automatically created
  USER_NOTE_ VARCHAR(255) NOT NULL,
  TASK_DATA_ TEXT NOT NULL,
  ACQUIRED_BY_ INTEGER NOT NULL, -- secondary index automatically created since it is FK
  CREATED_BY_ INTEGER NOT NULL, -- secondary index automatically created since it is FK
  CONSTRAINT FK_ACQUIRED_BY_ FOREIGN KEY(ACQUIRED_BY_) REFERENCES SCHEMA1.USERS(ID_),
  CONSTRAINT FK_CREATED_BY_ FOREIGN KEY(CREATED_BY_) REFERENCES SCHEMA1.USERS(ID_)
);

-- create index for fulltext seach upon column USER_NOTE_ in table SCHEMA2.TASKS
CALL FT_CREATE_INDEX('SCHEMA2', 'TASKS', 'USER_NOTE_');