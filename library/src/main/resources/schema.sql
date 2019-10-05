DROP TABLE IF EXISTS PERSONS;
CREATE TABLE PERSONS(ID UUID PRIMARY KEY, NAME VARCHAR(255));

DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(ID UUID PRIMARY KEY, NAME VARCHAR(255));

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(
ID UUID PRIMARY KEY,
NAME VARCHAR(255),
GENRE VARCHAR(255),
ISBN VARCHAR(255),
AUTHOR_ID UUID );

 ALTER TABLE BOOKS
    ADD FOREIGN KEY (AUTHOR_ID)
    REFERENCES AUTHORS;


